package com.example.data.entity;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.common.UserRole;
import com.example.dto.request.TestMemberRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, of = "username")
public class TestMember extends BaseEntity implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 11, nullable = false, insertable = false)
	private int id;
	
	@Column(length = 20, nullable = false, unique = true, name = "nickname")
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		// 여기서는 유저 역할만 존재한다고 가정하고 유저 역할만 반환.
		GrantedAuthority userAuth = new SimpleGrantedAuthority(
			UserRole.USER.getRole()
		);
		
		return Arrays.asList(userAuth);
	}
	
	public static TestMember toEntity(TestMemberRequest memberRequest) {
		
		return TestMember.builder()
			.username(memberRequest.getUsername())
			.password(memberRequest.getPassword())
			.build();
	}
	
}
