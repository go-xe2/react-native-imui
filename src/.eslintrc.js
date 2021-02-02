module.exports = {
    root: true,
    parser: 'babel-eslint',
    extends: ['standard', 'standard-react'],
    parserOptions: {
        ecmaVersion: 6,
        sourceType: 'module',
        ecmaFeatures: {
            jsx: true,
            experimentalObjectRestSpread: true,
        },
    },
    plugins: ['babel', 'react', 'promise'],
    globals: {
        __DEV__: true,
        fetch: true,
        FormData: true,
        Event: true,
    },
    rules: {
        indent: [2, 4, { SwitchCase: 1 }],
        'react/jsx-indent': [2, 4],
        'react/jsx-indent-props': [2, 4],
        'jsx-quotes': [2, 'prefer-single'],
        'max-len': 0,
        'handle-callback-err': 0,
        'operator-linebreak': 0,

        'no-unused-vars': [2, { vars: 'all', args: 'after-used' }], // 不能有声明后未被使用的变量或参数
        'object-property-newline': 0,
        'react/jsx-no-bind': 0,
        'react/prop-types': 0,
        'comma-dangle': [2, 'always-multiline'],
        'object-curly-spacing': [2, 'always'],
        'prefer-promise-reject-errors': 0,
        'space-unary-ops': 0,
        'no-unused-expressions': 0,
        'no-useless-return': 0,
        'standard/no-callback-literal': 0,
        'import/first': 0,
        'import/export': 0,
        'no-mixed-operators': 0,
        'no-use-before-define': 0, // 未定义前不能使用
        'no-spaced-func': 2, // 函数调用时 函数名与()之间不能有空格
        'no-sparse-arrays': 2, // 禁止稀疏数组， [1,,2]
        'no-this-before-super': 0, // 在调用super()之前不能使用this或super
        'no-undef': 1, // 不能有未定义的变量
        'no-useless-call': 2, // 禁止不必要的call和apply
        'no-warning-comments': [
            1,
            { terms: ['todo', 'fixme', 'xxx'], location: 'start' },
        ], // 不能有警告备注
        complexity: [0, 3], // 循环复杂度
        'consistent-this': [2, 'that'], // this别名
        'default-case': 2, // switch语句最后必须有default
        'eol-last': 2, // 文件以单一的换行符结束
        'spaced-comment': 2, // 注释风格要不要有空格什么的
        'lines-around-comment': 0, // 行前行后备注
        'no-inline-comments': 0, // 禁止行内备注
        'use-isnan': 2, // 禁止比较时使用NaN，只能用isNaN()
        // 必须使用分号
        semi: [2, 'always'],

        // 允许使用==
        eqeqeq: 2,

        // 不允许使用tab
        'no-tabs': 2,

        // 函数圆括号之前必须有空格
        'space-before-function-paren': [1, 'always'],

        // 不要求块内空格填充格式
        'padded-blocks': 0,

        // 不限制变量一起声明
        'one-var': 0,

        // debugger使用
        'no-debugger': 0,

        // 开发模式允许使用console
        'no-console': 0,

        // 条件语句中复制操作符需要用圆括号括起来
        'no-cond-assign': [2, 'except-parens'],

        // 允许使用条件表达式使用常量
        'no-constant-condition': 0,

        // 单行可忽略大括号，多行不可忽略
        curly: [2, 'multi-line'],

        // 不允许使用var变量
        'no-var': 2,

        // 不允许出现多个空格
        'no-multi-spaces': [
            'error',
            {
                ignoreEOLComments: true,
            },
        ],
        camelcase: 0,

        // 对象字面量的键值空格风格
        'key-spacing': 2,

        // if语句包含一个return语句， else就多余
        'no-else-return': 2,

        // 建议将经常出现的数字提取为变量
        'no-magic-numbers': [
            0,
            {
                ignoreArrayIndexes: true,
            },
        ],

        // 不允许重复声明变量
        'no-redeclare': [
            2,
            {
                builtinGlobals: true,
            },
        ],

        // 立即执行函数风格
        'wrap-iife': [2, 'inside'],

        // 不允许圆括号中出现空格
        'space-in-parens': [2, 'never'],

        // 确保运算符周围有空格
        'space-infix-ops': 2,

        // 强制点号与属性同一行
        'dot-location': [2, 'property'],

        // 强制单行代码使用空格
        'block-spacing': [2, 'always'],

        // 约束for-in使用hasOwnProperty判断
        'guard-for-in': 0,

        // 采用one true brace style大括号风格
        'brace-style': [
            2,
            '1tbs',
            {
                allowSingleLine: true,
            },
        ],

        // 统一逗号周围空格风格
        'comma-spacing': [
            2,
            {
                before: false,
                after: true,
            },
        ],

        // 禁止出现多个空行
        'no-multiple-empty-lines': [
            2,
            {
                max: 1,
                maxEOF: 2,
            },
        ],

        // 允许箭头函数不使用圆括号
        'arrow-parens': 0,

        // 规范generator函数的使用
        'generator-star-spacing': [
            2,
            {
                before: false,
                after: true,
            },
        ],
        'no-useless-rename': [
            'error',
            {
                ignoreDestructuring: false,
                ignoreImport: false,
                ignoreExport: false,
            },
        ],
    },
};
