package com.sv.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name="tbl_transaction")
public class Transaction {
	
	@Id
	@Column(name = "id_transaction")
	private String idTransaction;
	
	@NotNull
	@Column(name = "type_transaction")
	private String typeTransaction;
	
	@NotNull
	@Column(name = "date_transaction")
	private LocalDateTime dateTransaction;
	
	@NotNull
	@Column(name="amount_transaction")
	private BigDecimal amount;
	
	@NotNull
	@Column(name="number_account")
	private String numberAccount;
	
	@NotNull
	@Column(name = "new_available_account")
	private BigDecimal newAvailableAccount;
	
	

}
