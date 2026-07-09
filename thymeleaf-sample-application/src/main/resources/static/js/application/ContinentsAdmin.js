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
	
	
	async initEvents() {
		console.info("Init events for Continents Admin");
	}

	static async getInstance() {
		let continentsAdmin = new ContinentsAdmin();
		
		await continentsAdmin.init();
		
		return continentsAdmin;
	}
}