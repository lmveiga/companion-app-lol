package com.gmail.lucasmveigabr.companionlol.data.model

enum class Region {
    BR {
        override fun toString(): String {
            return "BR"
        }
    },
    NA {
        override fun toString(): String {
            return "NA"
        }
    },
    EUW {
        override fun toString(): String {
            return "EUW"
        }
    },
    EUNE {
        override fun toString(): String {
            return "EUNE"
        }
    },
    LAN {
        override fun toString(): String {
            return "LAN"
        }
    },
    LAS {
        override fun toString(): String {
            return "LAS"
        }
    },
    OCE {
        override fun toString(): String {
            return "OCE"
        }
    },
    RU {
        override fun toString(): String {
            return "RU"
        }
    },
    TR {
        override fun toString(): String {
            return "TR"
        }
    },
    JP {
        override fun toString(): String {
            return "JP"
        }
    },
    KR {
        override fun toString(): String {
            return "KR"
        }
    }
}