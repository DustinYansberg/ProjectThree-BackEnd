//package com.skillstorm.models;
//
//import java.util.Collection;
//import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class UserDetailModel implements UserDetails {
//    
//    private String username;
//    private String password;
//    private List<GrantedAuthority> authorities;
//
//    public UserDetailModel(Employee user){
//        this.username = user.getUsername();
//        this.password = user.getPassword();
//        this.authorities = user.getAuthorities()
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() { return this.authorities; }
//
//    @Override
//    public String getPassword() { return this.password; }
//
//    @Override
//    public String getUsername() { return this.username; }
//
//    @Override
//    public boolean isAccountNonExpired() { return false; }
//
//    @Override
//    public boolean isAccountNonLocked() { return false; }
//
//    @Override
//    public boolean isCredentialsNonExpired() { return false; }
//
//    @Override
//    public boolean isEnabled() { return false; }
//
//}
