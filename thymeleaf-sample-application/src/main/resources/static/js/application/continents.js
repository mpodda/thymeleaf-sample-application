import {ContinentsAdmin as ContinentsAdmin} from './ContinentsAdmin.js';

(async () => {

		async function init() {
		await ContinentsAdmin.getInstance();
	}
	
	await init();
})();