/**
 * 
 */

const Home = {
	oninit: function(vnode){
		if(currentUser == undefined || Object.keys(currentUser).length == 0){
			m.route.set("/login");
		}
	},

    view: () => {
        return m('div', {class:"container"}, [
            m(Header, {}, "Hello"),
            m(Timeline)
        ]);
    }
} 