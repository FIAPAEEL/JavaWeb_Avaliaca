package br.com.fiap.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Disciplina;
import br.com.fiap.util.SessionManager;

@ManagedBean
@RequestScoped
public class BoletimBean {

	private Curso curso;
	
	public BoletimBean(){
		
		if((Aluno) SessionManager.getObject("session_usuario")!=null){
			this.curso = ((Aluno) SessionManager.getObject("session_usuario")).getCurso();
		}
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public List<Disciplina> getDisciplinas(){
		
		
		if(curso != null){
			return new GenericDao(Disciplina.class).buscraDisciplinaPorCursoEAluno(curso.getId());
		}
		
		return null;
	}
	
}
