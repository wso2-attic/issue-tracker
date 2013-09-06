var render = function (theme, data, meta, require) {
    theme('issue_', {
        title: [
			{ partial:'title', context: data.title}
		],
		nav: [
			{ partial:'nav', context: data.nav}
		],
        header: [
            { partial:'header', context: data.header}
        ]
    });
};

