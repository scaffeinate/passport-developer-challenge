module.exports = function(grunt) {

  // 1. All configuration goes here
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: ['public/js/min/*', 'public/js/production.js', 'public/css/min/*', 'public/css/production.css'],
    concat: {
      css: {
        src: ['public/css/**/*.css'],
        dest: 'public/css/production.css'
      },
      js: {
        src: ['public/js/**/*.js', '!public/js/vendor/*.js'],
        dest: 'public/js/production.js'
      }
    },
    cssmin: {
      css: {
        src: 'public/css/production.css',
        dest: 'public/css/min/production.min.css'
      }
    },
    uglify: {
      js: {
        src: 'public/js/production.js',
        dest: 'public/js/min/production.min.js'
      }
    }
  });

  // 2. Where we tell Grunt we plan to use this plug-in.
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-uglify');

  // 3. Where we tell Grunt what to do when we type "grunt" into the terminal.
  grunt.registerTask('default', ['clean', 'concat', 'cssmin', 'uglify']);

};
