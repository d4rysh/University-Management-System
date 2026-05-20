package ua.foxminded.university_schedule_app.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Admin;
import ua.foxminded.university_schedule_app.JPARepositories.AdminRepository;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final AdminRepository adminRepository;
    
    @Autowired 
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Admin> getAllAdmins() {
        logger.info("Starting getAllAdmins method.");
        List<Admin> admins = adminRepository.findAll();
        logger.debug("Retrieved {} admins from database.", admins.size());
        return admins;
    }
    
    @Transactional(readOnly = true)
    public List<Admin> getAdminsByLastName(String lastName) {
        logger.info("Starting getAdminsByLastName method.");
        List<Admin> admins = adminRepository.findByLastName(lastName);
        logger.debug("Retrieved {} admins from database by last name {}.", lastName);
        return admins;
    }
}