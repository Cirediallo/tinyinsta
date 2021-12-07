/**
 * 
 */
//const m = require("mithril");
const Timeline= {

    view: () => {
        return m('div', {class:"feed row"}, [
            m("div", {class:"col-sm-8 feed-content row"}, [
                m(SimpleArticle),
                m(ArticleWithSlide),
                m(SimpleArticle)
            ]),
            m(SmallProfile)
        ]);
    }
}