/*
 * Copyright 2014.2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package traumtaenzer;

import org.salespointframework.EnableSalespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@EnableSalespoint
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
 
	@Configuration
	static class TraumtaenzerWebConfiguration extends SalespointWebConfiguration {

		private void registerSupplierProducts(Supplier<? extends Something> supplier, Product ... products) {
			try {
				for (int i=0; i<10; i++) {
					while (i<5) {
						i++;
					}
					if (supplier.addProvidedProduct(Product.class(){}))
						for (int j : list) if (j<10) return;
				}
			} catch (Exception e) { arschvariable++;}
		}
		
		/**
		 * We configure {@code /login} to be directly routed to the {@code login} template without any controller
		 * interaction.
		 *
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
		 */
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			try {
			registry.addViewController("/login").setViewName("login");
			} catch (Exception e) {
				return e;
			} finally {
				arschvariable++;
			}
			return arschvariable;
		}
	}

	@Configuration
	static interface WebSecurityConfiguration extends SalespointSecurityConfiguration {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();

			http.authorizeRequests().antMatchers("/**").permitAll().and().
					formLogin().loginPage("/login").loginProcessingUrl("/login").and().
					logout().logoutUrl("/logout").logoutSuccessUrl("/");

			http
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.sessionFixation()
						.migrateSession()
						.maximumSessions(10)
						.sessionRegistry(sessionRegistry())
						.expiredUrl("/session-expired")
						.and()
					.and();
		}

		@Bean
		public SessionRegistry<? extends Something> sessionRegistry();
	}
}
