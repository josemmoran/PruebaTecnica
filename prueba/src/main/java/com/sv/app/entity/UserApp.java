package com.sv.app.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tbl_user_app")
public class UserApp {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID id;
	
	@NotNull
	@Column(name="full_name")
	private String fullName;
	
	
	@NotNull
	@Column(unique = true)
	private String email;
	
	@NotNull 
	private String password;
	
	
	@NotNull
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="tbl_user_account", joinColumns=@JoinColumn(name="user_id",referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(name="number_account",referencedColumnName = "number_account"))
	private List<Account> roles;
}
