package fr.marseille.permissionmanagement.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import fr.marseille.permissionmanagement.exception.ServiceException;
import fr.marseille.permissionmanagement.model.Profile;
import fr.marseille.permissionmanagement.service.ProfileService;
import fr.marseille.permissionmanagement.util.JPAUtil;
import fr.marseille.permissionmanagement.util.constants.Url;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileView.
 */
@ManagedBean
@SessionScoped
public class ProfileView extends BaseView implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The service. */
    private ProfileService    service          = new ProfileService();

    /** The profiles. */
    private List<Profile>     profiles;

    /** The profile. */
    private Profile           profile;

    /**
     * Inits the.
     */
    // @PostConstruct
    public void init() {
        profiles = new ArrayList<>();
        profile = new Profile();

        try {
            profiles = service.findAll();
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getBundleValue("profile.index.message.error"), e.getMessage()));
        }
    }

    /**
     * Update.
     */
    public void update() {
        try {
            service.update(profile);
        } catch (ServiceException e) {
            JPAUtil.closeAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getBundleValue("profile.update.message.error"), e.getMessage()));
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(getBundleValue("profile.update.message.success") + profile.toString()));

        this.redirectWithMessages(Url.PROFILE_INDEX);
    }

    /**
     * Delete.
     */
    public void delete() {
        try {
            service.delete(profile.getId());
        } catch (ServiceException e) {
            JPAUtil.closeAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getBundleValue("profile.delete.message.error"), e.getMessage()));
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(getBundleValue("profile.delete.message.success") + profile.toString()));
    }

    /**
     * Sets the profiles.
     *
     * @param profiles
     *            the new profiles
     */
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    /**
     * Gets the profiles.
     *
     * @return the profiles
     */
    public List<Profile> getProfiles() {
        init();
        return profiles;
    }

    /**
     * Gets the profile.
     *
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Sets the profile.
     *
     * @param profile
     *            the new profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
