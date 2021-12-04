/**
 * DIALLO Ciré
 */

/*
import Grid from '@material-ui/core/Grid';
*/ 

const SignIn = () => {
	return (
		React.createElement( 
			"div", 
			{}, 
			[
				React.createElement( 
					"div", 
					{className: "loginpage_signin"}, 
					[
						React.createElement( 
							"input", {}, []
						), 
						React.createElement( 
							"input", {placeholder="Xhy"}, []
						), 
						React.createElement( 
							"button", 
							{}, 
							"Sign in"
						)
					]
				)
			]
		)
	)
}

const Login = () => {
	return (
		React.createElement(
			"div", 
			{},
			[
				React.createElement(
					"div",
					{className: "wrapper"},
					[
						React.createElement(
							"div", 
							{className: "xs-31"}, 
							"La partie 1"
						), 
						React.createElement(
							"div", 
							{className: "xs-6"}, 
							[
								React.createElement(
									"div", 
									{className: "loginpage_main"},
									[
										/*
										React.createElement(
											"div", 
											{}, 
											[
												React.createElement( 
													"img", 
													{className: "ig_photo"}, 
													src=${`./images/bg.png`}
												)
											]
										), 
										*/
										React.createElement(
											"div", 
											{},
											[
												React.createElement(
													"div", 
													{className: "loginpage_right_component"}, 
													[
														React.createElement(
															"h5", 
															{className: "instagram"}, 
															"Instagram"
														), 
														React.createElement(SignIn)
													]
												)
											]
										)
										
									]
								),
								
								
							]
						),
						React.createElement(
							"div", 
							{className: "xs-32"}, 
							"La partie 3"
						)
					]
				)
			]
		)
	);
}


const App = () => {
	return React.createElement(
		"div",
		
		{},[
			React.createElement(Login)
		]
		
	);
};

ReactDOM.render(React.createElement(App), document.getElementById('root'));