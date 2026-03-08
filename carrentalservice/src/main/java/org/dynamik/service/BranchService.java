package org.dynamik.service;

import org.apache.commons.collections4.CollectionUtils;
import org.dynamik.exception.ResourceNotFoundException;
import org.dynamik.model.Branch;
import org.dynamik.model.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class BranchService {
    private static  final ConcurrentHashMap<String, Branch> branches = new ConcurrentHashMap<>();


    public void save(Branch branch) {
        branches.put(branch.getId(), branch);
    }

    public Branch findById(String id) {
        return branches.get(id);
    }

    public void addVehicle(String branchId, Vehicle vehicle) throws ResourceNotFoundException {
        Branch branch = branches.get(branchId);
        if (branch == null) {
            throw new ResourceNotFoundException("branch not present");
        }

        if (CollectionUtils.isEmpty(branch.getVehicles())) {
            branch.setVehicles(new ArrayList<>(Arrays.asList(vehicle)));
        }
    }
}
