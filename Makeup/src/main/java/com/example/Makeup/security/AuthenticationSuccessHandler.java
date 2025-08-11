package com.example.Makeup.security;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  //    @Override
  //    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse
  // response, Authentication authentication) throws ServletException, IOException {
  //        boolean isAdmin = authentication.getAuthorities().stream()
  //                .anyMatch(grantedAuthority ->
  // grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
  //        if (isAdmin) {
  //            setDefaultTargetUrl("/admin/home");
  //        } else {
  //            setDefaultTargetUrl("/home");
  //        }
  //        super.onAuthenticationSuccess(request, response, authentication);
  //    }
}
