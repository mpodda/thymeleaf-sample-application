import {AdminBase as AdminBase} from '[(@{/})]js/application/AdminBase.js';

export class [(${instanceClassName})]Admin extends AdminBase {
	constructor () {
		super();
		
		this.SessionAttribute = "[(${sessionAttribute})]";
		
		this.AddValueUrl = "[(${addValueUrl})]";
		this.ListValueUrl = "[(${listValueUrl})]";
		this.SaveValueUrl = "[(${saveValueUrl})]";
		this.EditValueUrl = "[(${editValueUrl})]";
		
		this.AddValueRole = "[(${addValueRole})]";
		this.EditValueRole = "[(${editValueRole})]";
	}
	
	async initEvents() {
		console.info("Init events for [(${instanceClassName})]Admin");
	}

	static async getInstance() {
		let [(${sessionAttribute})]Admin = new [(${instanceClassName})]Admin();
		
		await [(${sessionAttribute})]Admin.init();
		
		return [(${sessionAttribute})]Admin;
	}
}

(async () => {

	async function init() {
		await [(${instanceClassName})]Admin.getInstance();
	}
	
	await init();
})();