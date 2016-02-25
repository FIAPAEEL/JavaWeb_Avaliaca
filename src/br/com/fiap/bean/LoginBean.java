package br.com.fiap.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.NoResultException;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Login;
import br.com.fiap.entity.Professor;
import br.com.fiap.util.SessionManager;

@ManagedBean
@RequestScoped
public class LoginBean {

	private String login;
	private String senha;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String cadastarLogin(){
		
		return null;
		
	}
	
	public String fazerLogin(){
		try {
			
			if (!login.contains("@")) {
				if (Integer.parseInt(login) >= 40000) {
					
					Aluno aluno = new GenericDao<Aluno>(Aluno.class).fazerLogin(login, senha);

					if (aluno != null) {
						SessionManager.createNewSession();
						SessionManager.setAttribute("session_usuario", aluno);
						if(aluno.getpLogin().equals("0")){
							return "alunos/cadSenhaAluno";
						}
						return "alunos/menu";
					}
				} else {
										
					Professor professor = new GenericDao<Professor>(Professor.class).fazerLogin(login, senha);

					if (professor != null) {
						SessionManager.createNewSession();
						SessionManager.setAttribute("session_usuario", professor);
						return "admin/menu";
					}
				}
			} else {
				
				Login loginn = new GenericDao<Login>(Login.class).fazerLogin(login, senha);
				
				if (loginn != null) {
					SessionManager.createNewSession();
					SessionManager.setAttribute("session_usuario",loginn);
					return "admin/menu";
				}
			}
			
			return "login.faces";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "erro";
		}catch (NoResultException e) {
			e.printStackTrace();
			return "erro";
		}
	}
	
	public String fazerLogOff(){
		
		SessionManager.logOff();
		
		return "/login";
	}
}
