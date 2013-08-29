var render = function (theme, data, meta, require) {
    theme('project', {
        title: [
			{ partial:'title', context: data.title}
		],
		nav: [
			{ partial:'addProjectNav', context: data.nav}
		],
		body: [
			{ partial:'addProject', context: data.body}
		]
    });
};

