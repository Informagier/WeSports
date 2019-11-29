package de.htwesports.wesports.profile

import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView

@Controller
class ProfileController {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @GetMapping("profiles/profile")
    @PreAuthorize("isAuthenticated()")
    fun getProfiles(model: ModelMap, request: WebRequest):ModelAndView{
        //em.find(Profile::class,1)
        val profile = getProfileByUsername(request.userPrincipal!!.name)
        if(profile==null){
        model.addAttribute("title", "Home")
        return ModelAndView("index")
        }
        //model.addAttribute("Profile",profile)
        return ModelAndView("redirect:/profiles/${profile.uri}")
    }
    private fun getProfileByUsername(username: String): Profile?{
        val user : User = (userRepository.findByEmail(username))?: return null
        user.profile?:return null
        return user.profile
    }
}
