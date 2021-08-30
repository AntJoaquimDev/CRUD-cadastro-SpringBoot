package curso.springboot.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.springboot.springboot.model.Pessoa;
import curso.springboot.springboot.model.Telefones;
import curso.springboot.springboot.repository.PessoaRepository;
import curso.springboot.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	@Autowired
	private PessoaRepository pessoRepository;
	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("cadastros/cadastroPessoa");
		modelAndView.addObject("pessoaobj", new Pessoa()); // obj vazio para retornar na tela
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarPessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {

		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId())); //para preecher no mtd ditar pessoa
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastros/cadastroPessoa");
			Iterable<Pessoa> pessoasIt = pessoRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaobj", pessoa); // obj para retornar na tela

			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // msg q vem la class model
			}
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		pessoRepository.save(pessoa);
		ModelAndView modelAndView = new ModelAndView("cadastros/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaobj", new Pessoa()); // obj vazio para retornar na tela
		modelAndView.addObject("msg", "Cadastro Salvo.");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarPessoas")
	public ModelAndView listarPessoas() {

		ModelAndView modelAndView = new ModelAndView("cadastros/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaobj", new Pessoa()); // obj vazio para retornar na tela
		return modelAndView;

	}

	@GetMapping("/editarPessoa/{idPessoa}")
	public ModelAndView editar(@PathVariable("idPessoa") Long idPessoa) {

		Optional<Pessoa> pessoa = pessoRepository.findById(idPessoa);
		ModelAndView andView = new ModelAndView("cadastros/cadastroPessoa");
		andView.addObject("pessoaobj", pessoa.get());

		return andView;

	}

	@GetMapping("/excluirPessoa/{idPessoa}")
	public ModelAndView excluir(@PathVariable("idPessoa") Long idPessoa) {

		pessoRepository.deleteById(idPessoa);
		ModelAndView andView = new ModelAndView("cadastros/cadastroPessoa");
		andView.addObject("pessoas", pessoRepository.findAll());
		andView.addObject("pessoaobj", new Pessoa()); // obj vazio para retornar na tela
									
		return andView;

	}

	@PostMapping("**/pesquisarPessoa") // culsatar por busca
	public ModelAndView pesquisar(@RequestParam("pesquisarNome") String pesquisarNome) {

		ModelAndView modelAndView = new ModelAndView("cadastros/cadastroPessoa");
		modelAndView.addObject("pessoas", pessoRepository.findPessoasByName(pesquisarNome));
		modelAndView.addObject("pessoaobj", new Pessoa());

		return modelAndView;

	}

	// MTDs para add telefones na Pessoa

	@GetMapping("/telefones/{idPessoa}")
	public ModelAndView telefones(@PathVariable("idPessoa") Long idPessoa) {

		Optional<Pessoa> pessoa = pessoRepository.findById(idPessoa);
		ModelAndView andView = new ModelAndView("cadastros/telefones");
		andView.addObject("telefones", telefoneRepository.getTelefones(idPessoa));
		andView.addObject("pessoaobj", pessoa.get());

		return andView;

	}

	// MTD addTelefonePessoa
	@PostMapping("**addTelefonesPessoa/{pessoaId}")
	public ModelAndView addTelefonesPessoa(@Valid Telefones telefones, BindingResult bindingResult,
			RedirectAttributes attributes, @PathVariable("pessoaId") Long pessoaId) {

		if (bindingResult.hasErrors()) {
			Pessoa pessoa = pessoRepository.findById(pessoaId).get();
			ModelAndView modelAndView = new ModelAndView("cadastros/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			// attributes.addFlashAttribute("msg","Campo Telefone não po ser vazio");
			modelAndView.addObject("msg", "Campo Telefone não po ser vazio");
			return modelAndView;
		}

		Pessoa pessoa = pessoRepository.findById(pessoaId).get();
		telefones.setPessoa(pessoa);
		telefoneRepository.save(telefones);
		ModelAndView modelAndView = new ModelAndView("cadastros/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaId));
		modelAndView.addObject("msg", "salvo com sucesso");
		return modelAndView;
	}

	@GetMapping("/excluirTelefone/{idTelefone}")
	public ModelAndView excluirTelefone(@PathVariable("idTelefone") Long idTelefone) {
		
		Pessoa pessoa = telefoneRepository.findById(idTelefone).get().getPessoa();

		telefoneRepository.deleteById(idTelefone);
		
		ModelAndView andView = new ModelAndView("cadastros/telefones");
		
		andView.addObject("pessoas", pessoRepository.findAll());
		andView.addObject("pessoaobj", pessoa);
		andView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		// obj vazio para retornar na tela
		return andView;

	}

}
