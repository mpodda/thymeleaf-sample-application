import {AdminBase as AdminBase} from './AdminBase.js';

export class PersonsAdmin extends AdminBase {
	constructor () {
		super();
		
		this.AddValueUrl = "/new-person";
		this.ListValueUrl = "/list-persons";
		this.SessionAttribute = "persons";
	}
	
	async initGridEvents() {
		const editPersonButtons = document.querySelectorAll('[role="edit-person"]');

		for (const editPersonButton of editPersonButtons) {
			editPersonButton.addEventListener("click", async () => {
				const personId = editPersonButton.getAttribute("data-id");
				
//				console.info(`Edit person with id ${personId}`);
				
				console.info("Person: ");
				this.editValue(personId);
			});
		}
	}
	
	async #initEvents() {
		const addContinentButton = document.querySelectorAll('[role="add-person"]')[0];
		addContinentButton.addEventListener("click", async () => {
			
			console.info("Person: ");
			await this.addValue();
		});
	
		await this.initGridEvents();
	}
	
	async init() {
		await this.#initEvents(); 
	}
	
	static async getInstance() {
		let personsAdmin = new PersonsAdmin();
		
		await personsAdmin.init();
	
		return personsAdmin;	
	}
}