package br.com.fiap.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Disciplina;
import br.com.fiap.entity.Escola;
import br.com.fiap.entity.Professor;
import br.com.fiap.util.SessionManager;

@ManagedBean
@ViewScoped
public class ProfessorBean{

	private Professor professor;
	private Escola escola;
	private int idEscola;
	private int idCurso;
	private ArrayList<Integer> idDisciplinas;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	private List<Disciplina> disciplinas;
	private List<Curso> cursos;
	private Map<Integer, Escola> mapEscola;
	private Map<Integer, Curso> mapCurso;
	private Map<Integer, Disciplina> mapDisciplinas;
	

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

	public String getButtonLink() {
		return buttonLink;
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
	
	
	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	
	public void setIdDisciplinas(ArrayList<Integer> idDisciplinas) {
		this.idDisciplinas = idDisciplinas;
	}
	
	public ArrayList<Integer> getIdDisciplinas() {
		return idDisciplinas;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public ProfessorBean(){
		this.professor = new Professor();
		this.escola = null;
		this.idEscola = -1;
		this.idDisciplinas = new ArrayList<Integer>();
	}
	
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	
	
	public String cadastrarProfessor(){
		
		this.escola = new GenericDao<Escola>(Escola.class).buscar(this.idEscola);
		
		try {
			
			if(this.escola == null){
				this.mensagem = "Erro ao cadastrar o professor, nenhuma escola foi selecionada.";
				this.buttonText = "Tentar novamente";
				this.buttonLink = "cadProfessor.faces";
				SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
				return "erro";
			}
			
			System.out.println(idDisciplinas.toString());
			for(int i : idDisciplinas){
				Disciplina d = new GenericDao<Disciplina>(Disciplina.class).buscar(i);
				professor.getDisciplinas().add(d);
			}
			
			GenericDao<Professor> dao = new GenericDao<Professor>(Professor.class);		
			escola.getProfessores().add(professor);
			
			professor.getEscolas().add(escola);
			
			System.out.println(idDisciplinas.toString());
			for(Disciplina d : professor.getDisciplinas()){
				System.out.println(d.getNome());
			}
					
			//dao.adicionar(professor);
			
			this.mensagem = "Professor cadastrado na escola "+this.escola.getNome()+" com sucesso.";
			this.buttonText = "Cadastrar outro professor";
			this.buttonLink = "cadProfessor.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "sucesso";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.mensagem = "Erro ao cadastrar o professor.";
			this.buttonText = "Tentar novamente";
			this.buttonLink = "cadProfessor.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "erro";
		}
	}
	
	public List<Escola> getListEscola(){
		GenericDao<Escola> dao = new GenericDao<Escola>(Escola.class);
		
		List<Escola> escolas = dao.listar();
		
		this.mapEscola = new HashMap<Integer, Escola>();
		
		for(Escola e : escolas){
			this.mapEscola.put(e.getId(), e);
		}
		
		return escolas;
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
	
	public void listDisciplinas(){
		
		this.disciplinas = new ArrayList<Disciplina>();
		
		System.out.println(this.idCurso);
		
		if(this.idCurso > 0){
			GenericDao<Curso> dao = new GenericDao<Curso>(Curso.class);
			
			Curso curso = dao.listarDisciplinasPorEscola(this.idCurso);
			
			this.mapDisciplinas = new HashMap<Integer, Disciplina>();
			
			for(Disciplina d : curso.getDisciplinas()){
				this.mapDisciplinas.put(d.getId(), d);
				this.disciplinas.add(d);
			}
		}
	}
}
