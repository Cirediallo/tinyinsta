/**
 * DIALLO Ciré
 */

const InitPost = {
	view: () => {
		return m('i', {class: 'nav-link', href: '#'}, [
			m('input', {
				type: 'file',
				id: 'postimage',
				accept: 'image/png, image/jpeg',
			}),
			m('img', {
				src: './images/svgs/add.svg'
			})
		])
	}
}