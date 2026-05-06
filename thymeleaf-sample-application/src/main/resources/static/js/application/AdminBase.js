import {intecommunication as app_itercom} from './thysa-application.js';

export class AdminBase {
	static currentInstances = [];
	
	#sessionAttribute = null;
	
	#addValueUrl = null;
	#editValueUrl = null;
	#listValueUrl = null;
	
	constructor() {
		AdminBase.currentInstances.push(this);
		
		app_itercom.onDataChange = async(sessionAttribute) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					await adminBaseInstance.initGridEvents();
				}
			}
		};
		
	}
	
	async editValue(id) {
		console.info("Edit Value with id", id);
	}
	
	async addValue() {
		console.info("Add value");
	}
	
	get SessionAttribute() {
		return this.#sessionAttribute;
	}
	
	set SessionAttribute(value) {
		this.#sessionAttribute = value;
	}

	get addValueUrl() {
		return this.#addValueUrl;
	}
	
	set addValueUrl(value) {
		this.#addValueUrl = value;
	}
	
	get editValueUrl() {
		return this.#editValueUrl;
	}
	
	set editValueUrl(value) {
		this.#editValueUrl = value;
	}

	get listValueUrl() {
		return this.#listValueUrl;
	}
	
	set listValueUrl(value) {
		this.#listValueUrl = value;
	}
}