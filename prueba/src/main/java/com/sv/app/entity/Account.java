package com.sv.app.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_account")
public class Account {

	@Id
	@NotNull
	@Column(name = "number_account")
	private String numberAccount;
	
	@NotNull
	@Column(name = "account_balance")
	private BigDecimal accountBalance;
}
