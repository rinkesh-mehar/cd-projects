package in.cropdata.farmers.app.config;

import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.filter.JwtAuthenticationEntryPoint;
import in.cropdata.farmers.app.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
//				.antMatchers("/authenticate").permitAll()
				.antMatchers("/basicFarmer/getOTP").permitAll()
				.antMatchers("/basicFarmer/validateOTP").permitAll()
				.antMatchers("/app/farmers/**").permitAll()
				.antMatchers("/basicFarmer/getTermsAndCondition").permitAll()
				.antMatchers("/basicFarmer/notifyFarmer").permitAll()
				.antMatchers("/basicFarmer/notifyFarmerForDelivery").permitAll()
				.antMatchers("/master/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.getWriter().write(new JSONObject()
						.put("errorCode", ErrorConstants.UNAUTHORIZED)
						.put("message", "Sorry, you are not authorized to use this resources")
						.put("error", "UnAuthorized User")
						.put("status", false).toString());
			} catch (JSONException jsonException) {
				jsonException.printStackTrace();
			}
		});
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

}