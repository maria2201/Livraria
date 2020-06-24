package livraria.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import livraria.model.*;


@Controller
public class LivrariaController {

	@Autowired
	private LivroRepo repo;
	
	@Autowired
	private GeneroRepo generoRepo;
	
	@Autowired
	private EditoraRepo editoraRepo;
	
	@Autowired
	private AutorRepo autorRepo;

	@GetMapping("/create")
	public String create(Model model) {
	model.addAttribute("livro", new Livro());
	model.addAttribute("generos", getGeneros());
	model.addAttribute("editoras", getEditoras());
	model.addAttribute("autor", getAutor());
	
	List<Livro> lista = repo.findAll();
	model.addAttribute("lista", lista);
	
	return "livroform";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Livro livro) {
	if(livro.getId() != 0) {
		Livro livroEditado = livro;
		livro = repo.findById(livroEditado.getId()).get();
		livro.setTitulo(livroEditado.getTitulo());
		livro.setDescricao(livroEditado.getDescricao());
		livro.setAno(livroEditado.getAno());
		livro.setPreco(livroEditado.getPreco());
		livro.setGenero(livroEditado.getGenero());
		livro.setEditora(livroEditado.getEditora());
		livro.setAutor(livroEditado.getAutor());
	}
		
	repo.save(livro);
	
	return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String listAll (Model model, @ModelAttribute Livro livro) {
		List<Livro> lista = repo.findAll();
		
		model.addAttribute("lista", lista);
		model.addAttribute("generos", getGeneros());
		model.addAttribute("editoras", getEditoras());
		model.addAttribute("autor", getAutor());
		
		return "livroform";
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable Integer id, Model model) {
		Livro livro = repo.findById(id).get();
		
		model.addAttribute("livro", livro);
		model.addAttribute("generos", getGeneros());
		model.addAttribute("editoras", getEditoras());
		model.addAttribute("autor", getAutor());
		
		return "livroform";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
		
		return "redirect:/list";
	}
	
	private List<Genero> getGeneros(){
		List<Genero> generos = generoRepo.findAll();
		
		return generos;
	}
	
	private List<Editora> getEditoras(){
		List<Editora> editoras = editoraRepo.findAll();
		
		return editoras;
		
	} 
	
	private  List<Autor> getAutor(){
		List<Autor> autor = autorRepo.findAll();
	     
		return autor;
	}
}
