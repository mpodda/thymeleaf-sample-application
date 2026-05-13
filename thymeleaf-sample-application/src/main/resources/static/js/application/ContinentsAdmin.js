import {AdminBase as AdminBase} from './AdminBase.js';

export class ContinentsAdmin extends AdminBase {
	constructor () {
		super();
		
		this.SessionAttribute = "continents";
		this.AddValueUrl = "/new-continent";
	}
	
	async initGridEvents() {
		/* Edit Continents */
		const editCondinentButtons = document.querySelectorAll('[role="edit-continent"]');

		for (const editCondinentButton of editCondinentButtons) {
			editCondinentButton.addEventListener("click", async () => {
				const condinentId = editCondinentButton.getAttribute("data-id");
				
				console.info("Continent: ");
				await this.editValue(condinentId);
			});
		}
	}
	
	async #initEvents() {
		/* Add Continent */
		const addContinentButton = document.querySelectorAll('[role="add-continent"]')[0];
		addContinentButton.addEventListener("click", async () => {
			
			console.info("Continent: ");
			await this.addValue();
		});

		await this.initGridEvents();
	}
	
	async init() {
		await this.#initEvents(); 
	}
	
	static async getInstance() {
		let continentsAdmin = new ContinentsAdmin();
		
		await continentsAdmin.init();
		
		return continentsAdmin;
	}
}