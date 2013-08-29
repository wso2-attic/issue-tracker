var render = function (theme, data, meta, require) {
    theme('issue', {
        title: [
			{ partial:'title', context: data.title}
		],
		nav: [
			{ partial:'nav', context: data.nav}
		],
		body: [
			{ partial:'addIssue', context: data.body}
		]
    });
};

