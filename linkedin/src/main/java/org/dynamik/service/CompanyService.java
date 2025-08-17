package org.dynamik.service;

import org.dynamik.dao.CompanyDao;
import org.dynamik.model.Company;

import java.util.List;
import java.util.Objects;

public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService() {
        this.companyDao = new CompanyDao();
    }

    public Company createCompany(Company company) {
        return companyDao.save(company);
    }

    public Company getCompanyById(String companyId) {
        return companyDao.findById(companyId);
    }

    public List<Company> getAllCompanies() {
        return companyDao.findAll();
    }

    public List<Company> searchCompanies(String name) {
        return companyDao.findByName(name);
    }

    public Company updateCompany(Company company) {
        if (company.getId() == null || company.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty for update");
        }
        return companyDao.save(company);
    }

    public void deleteCompany(String companyId) {
        companyDao.delete(companyId);
    }
}
