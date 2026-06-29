import {AdminBase as AdminBase} from './AdminBase.js';

export class ContinentsAdmin extends AdminBase {
	constructor () {
		super();
		
		this.SessionAttribute = "continents";
		
		this.AddValueUrl = "/new-continent";
		this.ListValueUrl = "/list-continents";
		this.SaveValueUrl = "/save-continent";
		this.EditValueUrl = "/edit-continent?continentId";
		
		this.AddValueRole = "add-continent";
		this.EditValueRole = "edit-continent";
		
	}
	
	
//	async initGridEvents() {
//		/* Edit Continents */
//		const editCondinentButtons = document.querySelectorAll('[role="edit-continent"]');
//
//		for (const editCondinentButton of editCondinentButtons) {
//			editCondinentButton.addEventListener("click", async () => {
//				const condinentId = editCondinentButton.getAttribute("data-id");
//				await this.editValue(condinentId);
//			});
//		}
//	}
	
//	async #initEvents() {
//		/* Add Continent */
//		const addContinentButton = document.querySelectorAll('[role="add-continent"]')[0];
//		addContinentButton.addEventListener("click", async () => {
//			await this.addValue();
//		});
//
//		await this.initGridEvents();
//	}
	
//	async init() {
//		await this.#initEvents(); 
//	}
	
	async initEvents() {
		console.info("Init events for Continents Admin");
	}

	static async getInstance() {
		let continentsAdmin = new ContinentsAdmin();
		
		await continentsAdmin.init();
		
		return continentsAdmin;
	}
}