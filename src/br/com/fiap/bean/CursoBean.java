package br.com.fiap.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Escola;
import br.com.fiap.util.SessionManager;

@ManagedBean
@RequestScoped
public class CursoBean{

	private Curso curso;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	private int idEscola;
	private Escola escola;
	private Map<Integer, Escola> mapEscola;
	
	public CursoBean(){
		this.curso = new Curso();
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public String getButtonLink() {
		return buttonLink;
	}
	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
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
	
	
	public String cadastrarCurso(){
		try {
			this.escola = mapEscola.get(idEscola);
			
			if(this.escola == null){
				this.mensagem = "Erro ao cadastrar o cuso, nenhuma escola foi selecionada.";
				this.buttonText = "Tentar novamente";
				this.buttonLink = "cadCurso.faces";
				SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
				return "erro";
			}
			
			GenericDao<Curso> dao = new GenericDao<Curso>(Curso.class);
			
			curso.getEscolas().add(escola);
			
			escola.getCursos().add(curso);
			
			dao.adicionar(curso);
			
			this.mensagem = "Curso cadastrado na escola "+this.escola.getNome()+" com sucesso.";
			this.buttonText = "Cadastrar outro Curso";
			this.buttonLink = "cadCurso.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "sucesso";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.mensagem = "Erro ao cadastrar o cuso.";
			this.buttonText = "Tentar novamente";
			this.buttonLink = "cadCurso.faces";
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
}
