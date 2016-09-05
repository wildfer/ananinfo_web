module.exports =function(grunt) {

	// 配置

	grunt.initConfig({

		pkg : grunt.file.readJSON('package.json'),

		concat : {

			css : {

				src: ['common.css','dialog.css','direct-buy.css','footer.css','global.css','header.css','index.css','member.css','quick-buy.css','special.css'],

				dest:'55st.css'

			}

		},

		cssmin: {

			css: {

				src:'55st.css',

				dest:'55st-min.css'

			},
			base: {

				src:'base.css',

				dest:'base-min.css'

			}
		}

	});

	// 载入concat和css插件，分别对于合并和压缩

	grunt.loadNpmTasks('grunt-contrib-concat');

	grunt.loadNpmTasks('grunt-css');

	// 默认任务

	grunt.registerTask('default', ['concat','cssmin']);

};