package fr.marseille.permissionmanagement.runtime;

import fr.marseille.permissionmanagement.exception.DAOException;
import fr.marseille.permissionmanagement.exception.ServiceException;
import fr.marseille.permissionmanagement.model.Permission;
import fr.marseille.permissionmanagement.model.Profile;
import fr.marseille.permissionmanagement.model.User;
import fr.marseille.permissionmanagement.service.PermissionService;
import fr.marseille.permissionmanagement.service.ProfileService;
import fr.marseille.permissionmanagement.service.UserService;

public class StartProfile {
    private static ProfileService    profileService    = new ProfileService();
    private static UserService       userService       = new UserService();
    private static PermissionService permissionService = new PermissionService();

    public static void main(String[] args) throws ServiceException, DAOException {

        // Start.generateDatabase();

        insertProfiles();
        affectUser();
        includePermission();

        // JPAUtil.closeAll();

    }

    protected static void affectUser() throws ServiceException, DAOException {
        User user = userService.findAll().get(0);
        Profile profile = profileService.findAll().get(0);

        profile.getUsers().add(user);
        profileService.update(profile);

        profileService.createProfiles();

    }

    protected static void includePermission() throws ServiceException, DAOException {

        Profile profile = profileService.findAll().get(0);
        Permission permission = permissionService.findAll().get(0);
        profile.getPermissions().add(permission);
        profileService.update(profile);

        profileService.createProfiles();

    }

    protected static void excludePermission() throws ServiceException, DAOException {

        Profile profile = profileService.findAll().get(0);
        Permission permission = permissionService.findAll().get(0);
        profile.getPermissions().remove(permission);
        profileService.update(profile);

        profileService.createProfiles();

    }

    protected static void insertProfiles() throws ServiceException {
        String[] applications = StartPermission.applications;
        for (String application : applications) {
            Profile profile = new Profile();
            profile.setName("Admin " + application);
            profile.setDescription("administrateur de l'application: " + application);
            profileService.save(profile);
            Profile profile1 = new Profile();
            profile1.setName("Editor " + application);
            profile1.setDescription("editeur de l'application: " + application);
            profileService.save(profile1);
            Profile profile2 = new Profile();
            profile2.setName("Guest " + application);
            profile2.setDescription("invite de l'application: " + application);
            profileService.save(profile2);
        }
    }
}
