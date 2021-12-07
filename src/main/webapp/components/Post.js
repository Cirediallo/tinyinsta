/**
 * 
 */
function post(){
	console.log("Post created: from Post component !");
}

const Post = {
	view: () => {
		/* class:"card col-12 d-none", id:"newPost" */
       return m('article',  [
				
                m('div', {class: 'card-header'},[
                    m('h6', {}, 'Post New Publication')
                ]),
                m('div', {class: 'card-body'},[
                    m("div", {class:"carousel slide", id:"postoutput"})
                ]),
                m('div', {class: 'card-footer'}, [
					/* class:"w-100", */
                    m('textarea', { id:"postDescription", placeholder: "write a description"}),
                    m('button', {class:'btn btn-secondary mt-1 float-right', onclick: post}, 'Publish')
                ])
           ]);

   }
}