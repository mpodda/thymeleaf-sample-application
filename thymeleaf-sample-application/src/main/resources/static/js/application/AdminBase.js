import {intecommunication as app_itercom} from './thysa-application.js';

import {pushFragment as pushFragment} from './thysa-application.js';

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
		
		pushFragment(this.#addValueUrl, this.#sessionAttribute);
	}
	
	get SessionAttribute() {
		return this.#sessionAttribute;
	}
	
	set SessionAttribute(value) {
		this.#sessionAttribute = value;
	}

	get AddValueUrl() {
		return this.#addValueUrl;
	}
	
	set AddValueUrl(value) {
		this.#addValueUrl = value;
	}
	
	get EditValueUrl() {
		return this.#editValueUrl;
	}
	
	set EditValueUrl(value) {
		this.#editValueUrl = value;
	}

	get ListValueUrl() {
		return this.#listValueUrl;
	}
	
	set ListValueUrl(value) {
		this.#listValueUrl = value;
	}
}