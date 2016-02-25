package br.com.fiap.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "professores", catalog = "dbescolas")
public class Professor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "NOME", length = 45)
	private String nome;
	
	@Column(name = "LOGIN", length = 45)
	private String login;
	
	@Column(name = "PLOGIN", length = 2)
	private String pLogin;
		
	@Column(name = "SENHA", length = 45)
	private String senha;
	
	@Column(name = "EMAIL", length = 45)
	private String email;
	
	@Column(name = "MATRICULA")
	private long matricula;
		
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "PROFESSOR_ESCOLA", catalog = "dbescolas", joinColumns = { @JoinColumn(name = "PROFESSOR_ID", nullable = false, updatable = true)}, inverseJoinColumns = { @JoinColumn(name = "ESCOLA_ID", nullable = false, updatable = true) })
	private Set<Escola> escolas = new HashSet<Escola>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "professor")
	private Set<Disciplina> disciplinas = new LinkedHashSet<Disciplina>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Escola> getEscolas() {
		return escolas;
	}

	public void setEscolas(Set<Escola> escolas) {
		this.escolas = escolas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getMatricula() {
		return matricula;
	}

	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getpLogin() {
		return pLogin;
	}

	public void setpLogin(String pLogin) {
		this.pLogin = pLogin;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
