package br.com.fiap.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Escola;
import br.com.fiap.util.SessionManager;

@ManagedBean
@RequestScoped
public class EscolaBean {
	
	private Escola escola;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	

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

	public EscolaBean() {
		this.escola = new Escola();
	}

	public Escola getEscola() {
		return escola;
	}

	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	
	public String incluirEscola(){
		try {
			GenericDao<Escola> dao = new GenericDao<Escola>(Escola.class);
			
			dao.adicionar(this.escola);
			
			this.mensagem = "Escola cadastrada com sucesso.";
			this.buttonText = "Cadastrar nova escola";
			this.buttonLink = "cadEscola.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "sucesso";
		} catch (Exception e) {
			
			e.printStackTrace();
			this.mensagem = "Ocorreu um erro ao cadastrar escola.";
			this.buttonText = "Tentar novamente";
			this.buttonLink = "cadEscola.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "erro";
		}
	}

	public List<Escola> getListEscola(){
		GenericDao<Escola> dao = new GenericDao<Escola>(Escola.class);
		
		List<Escola> escolas = dao.listar();
		
		return escolas;
	}	
}
