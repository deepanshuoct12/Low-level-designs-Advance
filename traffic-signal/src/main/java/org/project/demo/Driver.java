package org.project.demo;

import org.project.enums.Color;
import org.project.enums.Direction;
import org.project.model.DisplayScreen;
import org.project.model.Intersection;
import org.project.model.Light;
import org.project.model.Signal;
import org.project.service.DisplayScreenService;
import org.project.service.IntersectionService;
import org.project.service.SignalService;
import org.project.service.TrafficSignalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver {

    public void runDemo() {
        SignalService signalService = new SignalService();
        IntersectionService intersectionService = new IntersectionService();
        DisplayScreenService displayScreenService = new DisplayScreenService();
        TrafficSignalService trafficSignalService = TrafficSignalService.getInstance();

        DisplayScreen displayScreen = displayScreenService.create(new DisplayScreen());
        System.out.println("Created display screen with id " + displayScreen.getId());
        trafficSignalService.registerObserver(displayScreen);

        Map<Direction, Signal> signals = new HashMap<>();
        signals.put(Direction.NORTH, createSignal(signalService));
        signals.put(Direction.SOUTH, createSignal(signalService));
        signals.put(Direction.EAST, createSignal(signalService));
        signals.put(Direction.WEST, createSignal(signalService));

        Intersection intersection = new Intersection();
        intersection.setSignals(signals);
        intersectionService.create(intersection);

        for (Signal signal : signals.values()) {
            trafficSignalService.configureTimer(signal.getId(), Color.RED, 4);
            trafficSignalService.configureTimer(signal.getId(), Color.GREEN, 3);
            trafficSignalService.configureTimer(signal.getId(), Color.YELLOW, 2);
        }

        System.out.println("Starting intersection " + intersection.getId());
        trafficSignalService.startIntersection(intersection.getId());

        sleep(12_000);

        trafficSignalService.stopIntersection(intersection.getId());
        System.out.println("Stopped intersection " + intersection.getId());

        System.out.println("Forcefully overriding NORTH signal to RED");
        trafficSignalService.forceSignalColor(intersection.getId(), Direction.NORTH, Color.RED);
    }

    private Signal createSignal(SignalService signalService) {
        Signal signal = new Signal();
        signal.setLights(List.of(
                new Light(false, Color.RED),
                new Light(false, Color.YELLOW),
                new Light(false, Color.GREEN)
        ));
        return signalService.create(signal);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
