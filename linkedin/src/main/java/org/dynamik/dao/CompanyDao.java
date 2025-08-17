package org.dynamik.dao;

import org.dynamik.model.Company;

import java.util.*;

/**
 * Implementation of Company data access object using an in-memory HashMap.
 * This class provides CRUD operations for Company entities.
 */
public class CompanyDao implements IBaseDao<Company, String> {
    private static final Map<String, Company> companies = new HashMap<>();

    @Override
    public Company save(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }
        if (company.getId() == null || company.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty");
        }
        companies.put(company.getId(), company);
        return company;
    }

    @Override
    public List<Company> findAll() {
        return new ArrayList<>(companies.values());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty");
        }
        companies.remove(id);
    }

    @Override
    public Company findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty");
        }
        return companies.get(id);
    }

    /**
     * Find companies by name (case-insensitive partial match)
     * @param name the name or part of the name to search for
     * @return list of matching companies
     */
    public List<Company> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        String searchTerm = name.toLowerCase();
        List<Company> result = new ArrayList<>();
        for (Company company : companies.values()) {
            if (company.getName() != null && 
                company.getName().toLowerCase().contains(searchTerm)) {
                result.add(company);
            }
        }
        return result;
    }
}
