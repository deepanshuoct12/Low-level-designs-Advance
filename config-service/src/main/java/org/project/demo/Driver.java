package org.project.demo;

import org.project.model.Config;
import org.project.model.ConfigVersion;
import org.project.model.Environment;
import org.project.model.Service;
import org.project.service.impl.ConfigApplicationImpl;
import org.project.service.impl.ConfigServiceImpl;
import org.project.service.impl.ConfigVersionServiceImpl;
import org.project.service.impl.EnvironmentServiceImpl;
import org.project.service.impl.ServiceServiceImpl;

public class Driver {
    private final ConfigApplicationImpl configApplication;
    private final ConfigServiceImpl configService;
    private final ConfigVersionServiceImpl configVersionService;
    private final EnvironmentServiceImpl environmentService;
    private final ServiceServiceImpl serviceService;

    public Driver() {
        this.configApplication = ConfigApplicationImpl.getInstance();
        this.configService = new ConfigServiceImpl();
        this.configVersionService = new ConfigVersionServiceImpl();
        this.environmentService = new EnvironmentServiceImpl();
        this.serviceService = new ServiceServiceImpl();
    }

    public void runDemo() {
        System.out.println("=== Config Service Demo ===\n");

        // Create environment
        Environment env = Environment.builder()
                .name("Production")
                .description("Production environment")
                .isActive(true)
                .build();
        Environment createdEnv = environmentService.put(env);
        System.out.println("Created Environment: " + createdEnv.getName() + " with ID: " + createdEnv.getId());

        // Create service
        Service service = Service.builder()
                .serviceName("PaymentService")
                .build();
        Service createdService = serviceService.put(service);
        System.out.println("Created Service: " + createdService.getServiceName() + " with ID: " + createdService.getId());

        // Add config using ConfigApplication
        Config config = Config.builder()
                .key("payment.timeout")
                .description("Payment timeout in milliseconds")
                .environMentId(createdEnv.getId())
                .serviceId(createdService.getId())
                .isActive(true)
                .build();
        Config addedConfig = configApplication.addConfig(config);
        System.out.println("\nAdded Config: " + addedConfig.getKey() + " with ID: " + addedConfig.getId());

        // Get config
        Config retrievedConfig = configApplication.getConfig(addedConfig.getId());
        System.out.println("Retrieved Config: " + retrievedConfig.getKey());

        // Update config
        Config updatedConfig = Config.builder()
                .key("payment.timeout")
                .description("Updated payment timeout description")
                .environMentId(createdEnv.getId())
                .serviceId(createdService.getId())
                .isActive(true)
                .build();
        Config result = configApplication.updateConfig(addedConfig.getId(), updatedConfig);
        System.out.println("Updated Config: " + result.getKey());

        // Create config versions
        ConfigVersion version1 = ConfigVersion.builder()
                .configId(addedConfig.getId())
                .value("5000")
                .version(1)
                .changedBy("admin")
                .build();
        configVersionService.put(version1);
        System.out.println("\nCreated Version 1: " + version1.getValue());

        ConfigVersion version2 = ConfigVersion.builder()
                .configId(addedConfig.getId())
                .value("10000")
                .version(2)
                .changedBy("admin")
                .build();
        configVersionService.put(version2);
        System.out.println("Created Version 2: " + version2.getValue());

        ConfigVersion version3 = ConfigVersion.builder()
                .configId(addedConfig.getId())
                .value("15000")
                .version(3)
                .changedBy("admin")
                .build();
        configVersionService.put(version3);
        System.out.println("Created Version 3: " + version3.getValue());

        // Get previous version
        ConfigVersion previousVersion = configApplication.getPreviousVersion(addedConfig.getId(), createdEnv.getId());
        System.out.println("\nPrevious Version: " + previousVersion.getValue() + " (Version " + previousVersion.getVersion() + ")");

        // Rollback to previous version
        System.out.println("\nRolling back to previous version...");
        Config rollbackConfig = configApplication.rollbackToPreviousVersion(addedConfig.getId(), createdEnv.getId());
        System.out.println("Rollback completed. Config ID: " + rollbackConfig.getId());
        System.out.println("Config Key: " + rollbackConfig.getKey());
        
        // Get latest version after rollback
        ConfigVersion latestVersion = configVersionService.getLatestVersion(addedConfig.getId(), createdEnv.getId());
        if (latestVersion != null) {
            System.out.println("Config Value after rollback: " + latestVersion.getValue() + " (Version " + latestVersion.getVersion() + ")");
        }

        // Remove config
        System.out.println("\nRemoving config...");
        boolean removed = configApplication.removeConfig(addedConfig.getId());
        System.out.println("Config removed: " + removed);

        System.out.println("\n=== Demo Completed ===");
    }
}
