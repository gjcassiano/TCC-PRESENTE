app.service('AppService', function($http,$q) {

	//The $http serivce have any propreties
	// .get() .post() .put() .delete() .head() .jsonp() .patch()
	urlPath = "https://groovy-works-180421.appspot.com/_ah/api/presente/v1/";

	function makeRequest(pMethod, pPath, pData){
		var defer = $q.defer();
		pPath = urlPath + pPath;
		if (pData == '' | pData == 'undefined') {
			defer.resolve($http({method: pMethod, url:pPath}));
		} else {
			defer.resolve($http({method: pMethod, url:pPath, data:pData}));
		}
		return defer.promise;
	}

	//create the parameters to do request in backend.
	function GetRequest(pMethod, pPath, pData){
		return makeRequest(pMethod,pPath,pData);

	}

  	return {
  		GetRequest: GetRequest,
  	};
});
