//registring module
var twitter=angular.module("twitter",['ui.router']);
twitter.run(function($rootScope) {
  $rootScope.rootUserTName="default";
});

//configure
twitter.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider,$urlRouterProvider,$locationProvider){

    //setting default view to login
    $urlRouterProvider.when('','/twitterLogin');
    //state configurations
    $stateProvider
    //registration config
    .state('registration',{
        url:'/twitterRegistration',
        views:{
            'headerDashBoard':{
            templateUrl:'/pages/loginRegistrationHeader.html'
            },
            'middleView':{
                templateUrl:'/pages/registration.html',
                controller:'registerController'
            }
        }
    })
    //login config
    .state("twitterLogin",{
        url:'/twitterLogin',
        views:{
            'headerDashBoard':{
            templateUrl:'/pages/loginRegistrationHeader.html'
            },
            'middleView':{
                templateUrl:'/pages/twitterLogin.html',
                controller:'loginController'
            }
        }
    })

//timeLine config
    .state("timeLine",{
        url:'/timeLine',
        views:{
            'headerDashBoard':{
            templateUrl:'/pages/headerDashBoard.html'
            },
            'sideNavDashBoard':{
                templateUrl:'/pages/sideNavDashBoard.html',
                controller:'dashBoardController'
            },
            'middleView':{
                templateUrl:'/pages/timeLine.html',
                controller:'timeLineController'
            }
        }
    })

//stalkTweets config
    .state("stalkTweets",{
        url:'/stalkTweets',
        views:{
            'headerDashBoard':{
            templateUrl:'/pages/headerDashBoard.html'
            },
            'sideNavDashBoard':{
                templateUrl:'/pages/sideNavDashBoard.html',
                controller:'dashBoardController'
            },
            'middleView':{
                templateUrl:'/pages/stalkTweets.html',
                controller:'stalkTweetsController'
            }
        }
    })
    //profile config
    .state('profile',{
        url:'/profile',
        views:{
            'headerDashBoard':{
                templateUrl:'/pages/headerDashBoard.html',
                controller:'dashBoardController'
            },
            'sideNavDashBoard':{
                templateUrl:'/pages/sideNavDashBoard.html',
                controller:'dashBoardController'
            },
            'middleView':{
               templateUrl:'/pages/profile.html',
               controller:'profileController'
            }
        }
    })

    //friend suggestion
    .state('friendSuggestion',{
        url:'/friendSuggestion',
        views:{
            'headerDashBoard':{
                templateUrl:'/pages/headerDashBoard.html',
                controller:'dashBoardController'
            },
            'sideNavDashBoard':{
                templateUrl:'/pages/sideNavDashBoard.html',
                controller:'dashBoardController'
            },
            'middleView':{
               templateUrl:'/pages/friendSuggestion.html',
               controller:'friendSuggestionController'
            }
        }
    })

    //manage friends
    .state('manageFriends',{
        url:'/manageFriends',
        views:{
            'headerDashBoard':{
                templateUrl:'/pages/headerDashBoard.html',
                controller:'dashBoardController'
            },
            'sideNavDashBoard':{
                templateUrl:'/pages/sideNavDashBoard.html',
                controller:'dashBoardController'
            },
            'middleView':{
               templateUrl:'/pages/manageFriends.html',
               controller:'manageFriendsController'
            }
        }
    })

    //viewing likes for a tweet
    .state('viewLikes',{
        url:'/viewLikes',
        params:{tweetInfo:null},
        views:{
            'headerDashBoard':{
                templateUrl:'/pages/headerDashBoard.html',
                controller:'dashBoardController'
            },
            'sideNavDashBoard':{
                templateUrl:'/pages/sideNavDashBoard.html',
                controller:'dashBoardController'
            },
            'middleView':{
               templateUrl:'/pages/viewLikes.html',
               controller:"viewLikesController"
            }
        }
    })

    //viewing result for a search
    .state('searchResult',{
        url:'/searchResult',
        params:{result:null},
        views:{
                'headerDashBoard':{
                    templateUrl:'/pages/headerDashBoard.html',
                    controller:'dashBoardController'
                },
                'sideNavDashBoard':{
                    templateUrl:'/pages/sideNavDashBoard.html',
                    controller:'dashBoardController'
                },
                'middleView':{
                   templateUrl:'/pages/searchResult.html',
                   controller:"searchResultController"
                }
        }
    });
}]);


twitter.factory('authHttpResponseInterceptor',['$q','$state','$injector','$location',function($q,$state,$injector,$location){
	return {
		response: function(response){
			if (response.status === 401) {
				console.log("Response 401");
			}
			return response || $q.when(response);
		},
		responseError: function(rejection) {
		    console.log(rejection);
			if (rejection.status === 401) {
				console.log("Response Error 401",rejection);
				$injector.get('$state').go('twitterLogin');
			}
//			return $q.reject(rejection);
		}
	}
}]);
twitter.config(['$httpProvider',function($httpProvider) {
	//Http Intercpetor to check auth failures for xhr requests
	$httpProvider.interceptors.push('authHttpResponseInterceptor');
}]);




//controller for registeration
twitter.controller('registerController',function($scope,$http,$state){
    //on register button click
    $scope.registrationForm1=function(fName1,lName1,age1,city1,email1,mobile1,userTName1,userPassword1){
        var data={
            fName:fName1,
            lName:lName1,
            age:age1,
            mobile:mobile1,
            city:city1,
            userTName:userTName1,
            userPassword:userPassword1,
            email:email1
        };
        //posting on server
        $http.post('/registration',JSON.stringify(data)).then(function(response){
            if(response.data){
                if(response.data.message=="saved"){
                    alert("Registered Successfully");
                    $state.go("twitterLogin");
                }
                else{
                    alert(response.data.message);
                }
            }
        },function(response){
            alert("post method fail");
          }
        );
    };
});

//controller for login state
twitter.controller('loginController',function($rootScope,$state, $scope, $http, $location){
    var authenticate = function(credentials, callback) {
       var headers = credentials ? {authorization : "Basic "
            + btoa(credentials.username + ":" + $scope.credentials.password)
       } : {};

        //for getting logged in username
        $http.get('user', {headers : headers}).then(function(response) {
            if(response.data){
                if (response.data.name) {
                    $rootScope.authenticated = true;
                    $state.go('profile');
                }
                else {
                    $rootScope.authenticated = false;
                    $state.go('twitterLogin');
                }
                callback && callback();
            }
        },function(response){
            $rootScope.authenticated = false;
            callback && callback();
            }
        );
    }

    authenticate();
    $scope.credentials = {};
    //login form button
    $scope.loginFormButton = function(){
        authenticate($scope.credentials, function(){
            if ($rootScope.authenticated) {
                //redirect to profile page
                $state.go('profile');
                $scope.error = false;
            }
            else{
                $state.go('twitterLogin');
                $scope.error = true;
            }
        });
    };
});

//controller for dashboard
twitter.controller('dashBoardController',function($scope,$rootScope,$http,$stateParams,$state){
    //function for search user options
    $scope.searchUserMethod=function(e){
        if (e.which === 13){
            //searching in db for user
            var data=[
                $rootScope.rootUserTName,$scope.search
            ];
            $http.post("/searchUser",data).then(function(response){
                if(response.data){
                    $scope.searchResult=response.data;
                    var par={result:$scope.searchResult};
                    //viewing result of search
                    $state.go('searchResult',par);
                }
            },function(response){
                alert("server down");
               }
            );
        }
    };
});

//controller for profile
twitter.controller('profileController',function($scope,$http,$stateParams,$rootScope){
    //fetching details from DB
    function onLoad(){
            $http.get('/user').then(function(response){
                if(response.data){
                    if (response.data.name) {
                        $rootScope.authenticated = true;
                        $rootScope.rootUserTName=response.data.name;
                        $scope.userTName1=$rootScope.rootUserTName;
                        var data={
                            userTName:$scope.userTName1
                        };
                        //fetching profile details
                        $http.post('/viewProfile',JSON.stringify(data)).then(function(response){
                            if(response.data){
                                $scope.userData=response.data;
                                //populating text boxes
                                $scope.fName=$scope.userData.fName;
                                $scope.lName=$scope.userData.lName;
                                $scope.userTName=$scope.userData.userTName;
                                $scope.email=$scope.userData.email;
                                $scope.about=$scope.userData.about;
                                $scope.mobile=Number($scope.userData.mobile);
                                $scope.age=$scope.userData.age;
                                $scope.city=$scope.userData.city;
                            }
                        },function(response){
                            alert("cant load user details");
                          }
                        );
                    }
                    else {
                        $rootScope.authenticated = false;
                        $state.go('twitterLogin');
                    }
                }
            },function(response){
                alert("dont know");
                }
            );
    }
    window.onLoad=onLoad();
    //for saving edit personal details
    $scope.userDetailsSave=function(fName1,lName1,age1,city1,email1,mobile1,userTName1,about1){
        var data={
            fName:fName1,
            lName:lName1,
            age:age1,
            city:city1,
            email:email1,
            mobile:mobile1,
            userTName:userTName1,
            about:about1
        };

        $http.post('/userDetails',JSON.stringify(data)).then(function(response){
            if(response.data)
            {
                if(!alert('Saved!')){window.location.reload();}
            }
        },function(response){
            {
                alert("try later");
            }
         }
        );
    };
});

//controller for friend suggestion
twitter.controller('friendSuggestionController',function($scope,$http,$stateParams,$rootScope){
    var suggestedList=[];
    function onLoad(){
        var data={
            userTName:$rootScope.rootUserTName
        };
        $scope.loaded=[];
        //searching friends with any thing in common
        $http.post('friendSuggestion',JSON.stringify(data)).then(function(response){
            if(response.data){
               suggestedList=response.data;
               if(suggestedList.length>10)
               {    //for showing 10 suggestions
                    for(var i=0;i<=9;i++){
                        $scope.loaded[i]=suggestedList[i];
                    }
               }
               else{
                    //for showing 10 suggestions
                        $scope.loaded=suggestedList.slice();
               }
            }
            else{
                alert("no new suggestions");
            }
        },function(response){
            alert("server down");
            }
        );
    };
    window.onLoad=onLoad();

   //adding a friend(start following)
   $scope.startFollowing=function(toFollow){
        var data=[

           $rootScope.rootUserTName,toFollow
        ];
        //post a request
        $http.post("/startFollowing",data).then(function(response){
            if(response.data){
                if(response.data.added==data[1]){
                    alert("you started following"+data[1]+"");
                    onLoad();
                }
                else{
                    alert("cant follow");
                }
            }
        },function(response){
            alert("server down");
        }
        );
   };
});

//CONTROLLER FOR MANAGING FRIENDS
twitter.controller("manageFriendsController",function($scope,$http,$stateParams,$rootScope){
    function onLoad(){
        $http.get('/user').then(function(response){
            if(response.data){
                if (response.data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.rootUserTName=response.data.name;
                    var data=$rootScope.rootUserTName;
                    $scope.friendList=[];
                    //searching friends with any thing in common
                    $http.post('/manageFriends',data).then(function(response){
                        if(response.data){
                           $scope.friendList=response.data;
                           /*alert(response.data);*/
                        }
                    },function(response){
                        alert("server down");
                        }
                    );
                }
                else {
                    $rootScope.authenticated = false;
                    $state.go('twitterLogin');
                }
            }
        },function(response){
            alert("dont know");
            }
        );
    };
    window.onLoad=onLoad();

    //unfollow a user
    $scope.unFollow=function(unfollow){
        var data=[
            $rootScope.rootUserTName,unfollow
        ];

        $http.post('/unFollow',data).then(function(response){
            if(response.data){
                if(!alert('UNFOLLOW SUCCESSFULLY!')){window.location.reload();}
            }
        },function(response){
            alert("server down");
          }
        );
    }
});

//controller for TimeLine
twitter.controller("timeLineController",function($state,$scope,$http,$stateParams,$rootScope){
    //posting a new tweet
    $scope.postTweet=function(){
        if($scope.tweet==null){
            alert("put your thought in the box");
        }
        else{
            var data=[
                $rootScope.rootUserTName,$scope.tweet
            ];
            $http.post("/postTweet",data).then(function(response){
                if(response.data){
                    if(!alert('POSTED SUCCESSFULLY!')){window.location.reload();}
                }
            },function(response){
                alert("server down");
              }
            );
        }
    };

    //rendering tweets for time line
    function onLoad(){
        $http.get('/user').then(function(response){
            if(response.data){
                if (response.data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.rootUserTName=response.data.name;
                    $scope.yourTweets=[];
                    //searching friends with any thing in common
                    $http.post('/yourTweets',$rootScope.rootUserTName).then(function(response){
                        if(response.data){
                            $scope.yourTweets=response.data;
                            /*alert(response.data);*/
                        }
                    },function(response){
                        alert("server down");
                        }
                    );

                }
                else {
                    $rootScope.authenticated = false;
                    $state.go('twitterLogin');
                }
            }
        },function(response){
            alert("dont know");
            }
        );
    }

    window.onLoad=onLoad();

    //deleting a post
    $scope.deleteTweet=function(tweetId){
        var data=tweetId;
        //deleting a post
        $http.post('/deleteTweet',data).then(function(response){
            if(response.data){
                if(!alert('POSTED DELETED SUCCESSFULLY!')){window.location.reload();}
            }
        },function(response){
            alert("server down");
           }
        );
    }

    //viewing number of likes
    $scope.viewLikes=function(tweet){
        var tweet1={tweetInfo:tweet};
        $state.go('viewLikes',tweet1);
    }
});

twitter.controller("stalkTweetsController",function($scope,$http,$stateParams,$rootScope){
    //rendering stalktweets for time line
    function onLoad(){
        $http.get('/user').then(function(response){
            if(response.data){
                if (response.data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.rootUserTName=response.data.name;
                    $scope.stalkTweets=[];
                    //hitting the server
                    $http.post('/stalkTweets',$rootScope.rootUserTName).then(function(response){
                        if(response.data){
                            $scope.stalkTweets=response.data;
                            /*alert(response.data);*/
                        }
                    },function(response){
                        alert("server down");
                       }
                    );

                }
                else {
                    $rootScope.authenticated = false;
                    $state.go('twitterLogin');
                }
            }
        },function(response){
            alert("dont know");
            }
        );
    };
    window.onLoad=onLoad();

    function pageRefresh(){
        setInterval(function(){
            onLoad();
        },7000);
    }
    pageRefresh();
    //liking a tweet
    $scope.likeTweet=function(tweetId,followingTName){
        var data=[
            $rootScope.rootUserTName,tweetId,followingTName
        ];
        //hitting the server
        $http.post('/likeTweet',data).then(function(response){
            if(response.data){
                if(!alert('lIKED SUCCESSFULLY!')){window.location.reload();}
            }
        },function(response){
            alert("server down");
           }
        );
    }

    //unlike a tweet
    $scope.unLikeTweet=function(tweetId,followingTName){
        var data=[
            $rootScope.rootUserTName,tweetId,followingTName
        ];
        //hitting the server
        $http.post('/unLikeTweet',data).then(function(response){
            if(response.data){
                if(!alert('UNLIKED SUCCESSFULLY!')){window.location.reload();}
            }
        },function(response){
            alert("server down");
           }
        );
    }
});

//viewLikes of a tweet
twitter.controller("viewLikesController",function($scope,$http,$stateParams,$rootScope){
    function onLoad(){
        $scope.info=$stateParams.tweetInfo;
    }
    window.onLoad=onLoad();

});

//search Result
twitter.controller("searchResultController",function($scope,$http,$stateParams,$rootScope){
    function onLoad(){
        $scope.info=$stateParams.result;

    }
    window.onLoad=onLoad();

 //unfollow a user
 $scope.unFollowUser=function(unfollow){
    var data=[
        $rootScope.rootUserTName,unfollow
    ];

    $http.post('/unFollow',data).then(function(response){
        if(response.data){
            if(!alert('UNFOLLOW SUCCESSFULLY!')){window.location.reload();}
            //reload page here
        }
    },function(response){
        alert("server down");
      }
    );
 };

//start following a user
$scope.followUser=function(toFollow){
    var data=[
        $rootScope.rootUserTName,toFollow
    ];
    //post a request
    $http.post("/startFollowing",data).then(function(response){
        if(response.data){
            if(response.data.added==data[1]){
                if(!alert('you started following'+data[1]+"")){window.location.reload();}
                onLoad();
            }
            else{
                alert("cant follow");
            }
        }
    },function(response){
        alert("server down");
      }
    );
};
});





