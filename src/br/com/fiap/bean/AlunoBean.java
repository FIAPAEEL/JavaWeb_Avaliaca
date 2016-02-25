package br.com.fiap.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Escola;
import br.com.fiap.util.SessionManager;

@ManagedBean
@ViewScoped
public class AlunoBean {

	private Aluno aluno;
	private String novaSenha;
	private Curso curso;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	private int idEscola;
	private int idCurso;
	private List<Curso> cursos;
	private Map<Integer, Curso> mapCurso;

	public String getButtonLink() {
		return buttonLink;
	}
	

	public String getNovaSenha() {
		return novaSenha;
	}


	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}


	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public int getIdEscola() {
		return idEscola;
	}

	public void setIdEscola(int idEscola) {
		this.idEscola = idEscola;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	
	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public AlunoBean(){
		this.aluno = new Aluno();
	}
	
	public String cadAluno(){
		try {

			this.curso = mapCurso.get(idCurso);			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date date = sdf.parse(SessionManager.get("dataNasc"));
			
			aluno.setDataNasc(sdf.parse(SessionManager.get("dataNasc")));
			aluno.setDataMatricula(sdf.parse(SessionManager.get("dataMat")));
			aluno.setLogin(String.valueOf(aluno.getMatricula()));
			sdf = new SimpleDateFormat("ddMMyyyy");
			
			aluno.setSenha(sdf.format(date));
			
			aluno.setpLogin("0");
			
			aluno.setCurso(this.curso);
			
			new GenericDao<Aluno>(Aluno.class).adicionar(this.aluno);
			
			this.mensagem = "Aluno cadastrado com sucesso.";
			this.buttonText = "Cadartar outro aluno";
			this.buttonLink = "cadAluno.faces";
			
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "sucesso";
		} catch (Exception e) {
			e.printStackTrace();
			this.mensagem = "Náo foi possivel cadastrar o aluno"+aluno.getNome();
			this.buttonText = "Teantar novamente";
			this.buttonLink = "cadAluno.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "erro";
		}
	}
	
	
	public String recadastrarSenha(){
		
		try {
			this.aluno = (Aluno) SessionManager.getObject("session_usuario");
			
			aluno.setSenha(this.novaSenha);
			aluno.setpLogin("1");
			
			new GenericDao<Aluno>(Aluno.class).adicionar(this.aluno);
			
			return "menu";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			this.mensagem = "Erro ao tentar recadastrar a senha";
			this.buttonLink = "/JavaWeb_Avaliacao/login.faces";
			this.buttonText = "Ir para Login";
			
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			
			return "erro";
		}
	}

	public List<Aluno> getlistAlunos(){
		
		GenericDao<Aluno> dao = new GenericDao<Aluno>(Aluno.class);		 
		
		return dao.listar();
	}
	
	
	public void listCursos(){
				
		this.cursos = new ArrayList<Curso>();
		
		if(this.idEscola > 0){
			GenericDao<Escola> dao = new GenericDao<Escola>(Escola.class);
			
			Escola escola = dao.listarCursosPorEscola(this.idEscola);
			
			this.mapCurso = new HashMap<Integer, Curso>();
			
			for(Curso c : escola.getCursos()){
				this.mapCurso.put(c.getId(), c);
				this.cursos.add(c);
			}
		}
	}
}
