var render = function (theme, data, meta, require) {
    theme('issue', {
        title: [
			{ partial:'title', context: data.title}
		],
		nav: [
			{ partial:'issueNav', context: data.nav}
		],
		body: [
			{ partial:'issueBody', context: data.body}
		]
    });
};

