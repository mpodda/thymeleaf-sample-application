import {AdminBase as AdminBase} from './AdminBase.js';

export class CitiesAdmin extends AdminBase {
	constructor() {
		super();
		
		this.SessionAttribute = "cities";
		
		this.AddValueUrl = "/new-city";
		this.ListValueUrl = "/list-cities";
		this.SaveValueUrl = "/save-city";
		this.EditValueUrl = "/edit-city?cityId";
		
		this.AddValueRole = "add-city";
		this.EditValueRole = "edit-city";
	}
	
	
	/*
	async selectionChange(data) {
		console.info("data=", data);
		
		switch(data.name) {
			case "continent" :
				console.info ("Filter countries by ", data.value);
			break;
		}
	}
	*/
	
	
	static async getInstance() {
		let citiesAdmin = new CitiesAdmin();
		
		await citiesAdmin.init();
		
		return citiesAdmin;
	}
}