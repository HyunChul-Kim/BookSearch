package com.chul.booksearch.presentation.search

enum class OperatorType {
    /**
     * 기본 검색
     */
    OP_TYPE_NONE,

    /**
     * a|b : a 또는 b가 포함 된 검색
     */
    OP_TYPE_OR,

    /**
     * a-b : a가 포함 된 검색에서 b가 포함 된 검색 제외
     */
    OP_TYPE_NOT
}