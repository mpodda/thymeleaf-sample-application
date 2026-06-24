import {intecommunication as app_itercom, postForm} from './thysa-application.js';
import {pushFragment as pushFragment, createHTMLFragmentFromRole, setContentFromFragment} from './thysa-application.js';
import {pagingAndSortingBySessionAttribute as pagingAndSortingBySessionAttribute} from './thysa-application.js';
import {HttpStatus}  from './thysa-application.js';
import {formComponents as formComponents}  from './thysa-application.js';


export class AdminBase {
	static currentInstances = [];
	
	#sessionAttribute = null;
	
	#addValueUrl = null;
	#editValueUrl = null;
	#listValueUrl = null;
	#saveValueUrl = null;
	
	#formComponents = null;
	
	constructor() {
		AdminBase.currentInstances.push(this);
		
		app_itercom.onDataChange = async(sessionAttribute) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					await adminBaseInstance.initGridEvents();
				}
			}
		};
		
		app_itercom.onPopFragment = async (sessionAttribute) => {
			await pagingAndSortingBySessionAttribute(sessionAttribute);

			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					await adminBaseInstance.init();
				}
			}
		}
		
		app_itercom.onPushFragment = async (sessionAttribute) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === sessionAttribute) {
					adminBaseInstance.FormComponents = await formComponents();
				}
			}
		}
		
		app_itercom.onDataListInput = async (data) => {
			for (const adminBaseInstance of AdminBase.currentInstances) {
				if (adminBaseInstance.SessionAttribute === data.sessionAttribute) {
					if (!adminBaseInstance.FormComponents.isOptionValue(data.value, data.dataListId)) {
						await adminBaseInstance.filter(data);
					}
				}
			}
		}
	}
	
	/** 
	 * Rerenders form fields. Usually in case of User Input validation errors
	 */
	async #pushFormFields(htmlText) {
		let currentContentPlaceholder = null;
		
		let placeholders = document.querySelectorAll('[role="form-content"]');
		
		for (const placeholder of placeholders) {
			if (placeholder.getAttribute("form-content-id") === this.#sessionAttribute) {
				currentContentPlaceholder = placeholder;
				break;
			}
		}		
		
		const fieldsFragment = createHTMLFragmentFromRole(htmlText, 'form-content');
		
		await setContentFromFragment (fieldsFragment, currentContentPlaceholder);
		
		/* Re-init form fields events, etc. */
		await formComponents();
		
		/* 
		Collect all datalist related hidden fields (contain binding data) and dispatch event for each of them 
		in order to catch it the listener and put the corresponding value in to the 
		Datalist related text input element 
		*/
		const dataListHiddenElements = fieldsFragment.querySelectorAll('[role="datalist-hidden"]');
		
		for (const dataListHiddenElement of dataListHiddenElements) {
			dataListHiddenElement.dispatchEvent(new Event("change"));
		}
	}
	
	async #initSaveEvent(containerFragment) {
		const saveButton = containerFragment.querySelector("#btnSave");
		
		saveButton.addEventListener("click", async () => {
			const forms = containerFragment.getElementsByTagName("form");
			
			for (const form of forms) {
				if (form.getAttribute("role") === "form") {
					const httpRequest = await postForm(form, this.#saveValueUrl);
					
					if (httpRequest.status === Number (HttpStatus.OK.description)) {
						await pushFragment(this.#listValueUrl, this.#sessionAttribute);

						await app_itercom.onDataChange(this.#sessionAttribute);

						await app_itercom.onPopFragment(this.#sessionAttribute);
					} else {
						await this.#pushFormFields(httpRequest.responseText);						
					}
					
					break;
				}
			}
			
			console.info("Save");
		});		
	}
	
	async #initCancelEvent(containerFragment) {
		const cancelButton = containerFragment.querySelector("#btnCancel");
		
		cancelButton.addEventListener("click", async () => {

			await pushFragment(this.#listValueUrl, this.#sessionAttribute);
			
			await app_itercom.onDataChange(this.#sessionAttribute);
			
			await app_itercom.onPopFragment(this.#sessionAttribute);
			
		 	console.info("Cancel");
		});		
	}
	
	async editValue(id) {
		console.info("Edit Value with id", id);
		
		const formFragment = await pushFragment(`${this.#editValueUrl}=${id}`, this.#sessionAttribute);
		
		/* Save */
		await this.#initSaveEvent(formFragment);

		/* Cancel */
		await this.#initCancelEvent(formFragment);
		
		await app_itercom.onPushFragment(this.#sessionAttribute);
	}
	
	async addValue() {
		//console.info("Add value", "addValueUrl=", this.#addValueUrl, "sessionAttribute=", this.#sessionAttribute);
		
		const formFragment = await pushFragment(this.#addValueUrl, this.#sessionAttribute);

		/* Save */
		await this.#initSaveEvent(formFragment);
		
		/* Cancel */
		await this.#initCancelEvent(formFragment);
		
		await app_itercom.onPushFragment(this.#sessionAttribute);
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
	
	get SaveValueUrl () {
		return this.#saveValueUrl;
	}
	
	set SaveValueUrl(value) {
		this.#saveValueUrl = value;
	}
	
	get FormComponents() {
		return this.#formComponents;
	}
	
	set FormComponents(value) {
		this.#formComponents = value;
	}
}