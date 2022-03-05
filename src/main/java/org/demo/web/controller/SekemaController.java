
package org.demo.web.controller;

		import org.demo.jpa.Modal;
		import org.demo.jpa.Sekema;
		import org.demo.jpa.SekemaDetail;
		import org.demo.repository.ModalJpaRepository;
		import org.demo.repository.SekemaDetailJpaRepository;
		import org.demo.repository.SekemaJpaRepository;
		import org.demo.web.common.AbstractController;
		import org.demo.web.common.FormMode;
		import org.demo.web.common.Message;
		import org.demo.web.common.MessageType;
		import org.springframework.http.HttpStatus;
		import org.springframework.http.MediaType;
		import org.springframework.stereotype.Controller;
		import org.springframework.ui.Model;
		import org.springframework.validation.BindingResult;
		import org.springframework.web.bind.annotation.*;
		import org.springframework.web.servlet.mvc.support.RedirectAttributes;

		import javax.annotation.Resource;
		import javax.servlet.http.HttpServletRequest;
		import javax.validation.Valid;
		import java.math.BigDecimal;
		import java.util.List;
		import java.util.Map;

@Controller
@RequestMapping("/sekema")
public class SekemaController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "sekema";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "sekema/form";
	private static final String JSP_LIST   = "sekema/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/sekema/create";
	private static final String SAVE_ACTION_UPDATE   = "/sekema/update";

	//--- Main entity service
	@Resource
	private SekemaJpaRepository sekemaJpaRepository; // Injected by Spring
	@Resource
	private SekemaDetailJpaRepository sekemaDetailJpaRepository; // Injected by Spring
	@Resource
	private ModalJpaRepository modalJpaRepository; // Injected by Spring
	//--- Other service(s)

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public SekemaController() {
		super(SekemaController.class, MAIN_ENTITY_NAME );
		log("SekemaController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------

	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param sekema
	 */
	private void populateModel(Model model, Sekema sekema, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, sekema);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
			//--- Other data useful in this screen in "create" mode (all fields)
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
		}
		model.addAttribute("modalList", modalJpaRepository.findAll());
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of Sekema found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<Sekema> list = (List<Sekema>) sekemaJpaRepository.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new Sekema
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		Sekema sekema = new Sekema();
		populateModel( model, sekema, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing Sekema
	 * @param model Spring MVC model
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String formForUpdate(Model model, @PathVariable("id") Integer id ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model
		Sekema sekema = sekemaJpaRepository.findOne(id);
		populateModel( model, sekema, FormMode.UPDATE);
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param sekema  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid Sekema sekema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				Sekema pembelianHeaderCreated = sekemaJpaRepository.save(sekema);
				model.addAttribute(MAIN_ENTITY_NAME, pembelianHeaderCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, sekema.getId() );
			} else {
				populateModel( model, sekema, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "sekema.error.create", e);
			populateModel( model, sekema, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param sekema  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid Sekema sekema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				Sekema pembelianHeaderSaved = sekemaJpaRepository.save(sekema);
				model.addAttribute(MAIN_ENTITY_NAME, pembelianHeaderSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, sekema.getId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, sekema, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "sekema.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, sekema, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		log("Action 'delete'" );
		try {
			sekemaJpaRepository.delete(id);
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "sekema.error.delete", e);
		}
		return redirectToList();
	}

	@RequestMapping(value = "/get-modal-by-id",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Modal saveJquery(@RequestBody Modal params) {
		Modal result = modalJpaRepository.findOne(params.getId());
		result.setJumlah(params.getJumlah());
		result.setTotal(result.getHarga().multiply(BigDecimal.valueOf(result.getJumlah())));
		return result;
	}


	@RequestMapping(value = "/save-jquery",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String save(@RequestBody Sekema params) {
		if(params.getMode().equals("create")){
			for (SekemaDetail item : params.getListOfSekemaDetails()) {
				item.setId(null);
				item.setSekema(params);
			}
		}else{
			for (SekemaDetail item : params.getListOfSekemaDetails()) {
				item.setSekema(params);
			}
		}
		sekemaJpaRepository.save(params);
		return "1";
	}

}
