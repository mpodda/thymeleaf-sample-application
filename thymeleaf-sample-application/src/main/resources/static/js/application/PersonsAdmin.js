import {AdminBase as AdminBase} from './AdminBase.js';

export class PersonsAdmin extends AdminBase {
	constructor () {
		super();
		
		this.SessionAttribute = "persons";
		
		this.AddValueUrl = "/new-person";
		this.ListValueUrl = "/list-persons";
		this.SaveValueUrl = "/save-persons";
		this.EditValueUrl = "/edit-person?personId";

		this.AddValueRole = "add-person";
		this.EditValueRole = "edit-person";
	}
	
	static async getInstance() {
		let personsAdmin = new PersonsAdmin();
		
		await personsAdmin.init();
	
		return personsAdmin;	
	}
}