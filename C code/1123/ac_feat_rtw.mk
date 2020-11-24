###########################################################################
## Makefile generated for MATLAB file/project 'ac_feat'. 
## 
## Makefile     : ac_feat_rtw.mk
## Generated on : Mon Nov 23 00:10:26 2020
## MATLAB Coder version: 5.0 (R2020a)
## 
## Build Info:
## 
## Final product: ./ac_feat.lib
## Product type : static-library
## 
###########################################################################

###########################################################################
## MACROS
###########################################################################

# Macro Descriptions:
# PRODUCT_NAME            Name of the system to build
# MAKEFILE                Name of this makefile
# COMPILER_COMMAND_FILE   Compiler command listing model reference header paths
# CMD_FILE                Command file
# MODELLIB                Static library target

PRODUCT_NAME              = ac_feat
MAKEFILE                  = ac_feat_rtw.mk
MATLAB_ROOT               = C:/PROGRA~1/MATLAB/R2020a
MATLAB_BIN                = C:/PROGRA~1/MATLAB/R2020a/bin
MATLAB_ARCH_BIN           = $(MATLAB_BIN)/win64
MASTER_ANCHOR_DIR         = 
START_DIR                 = C:/Users/ieedara/Desktop/Harman_files/09.29.2020/codegen/lib/ac_feat
TGT_FCN_LIB               = ISO_C
SOLVER_OBJ                = 
CLASSIC_INTERFACE         = 0
MODEL_HAS_DYNAMICALLY_LOADED_SFCNS = 
RELATIVE_PATH_TO_ANCHOR   = .
COMPILER_COMMAND_FILE     = ac_feat_rtw_comp.rsp
CMD_FILE                  = ac_feat_rtw.rsp
C_STANDARD_OPTS           = -fwrapv
CPP_STANDARD_OPTS         = -fwrapv
MODELLIB                  = ac_feat.lib

###########################################################################
## TOOLCHAIN SPECIFICATIONS
###########################################################################

# Toolchain Name:          MinGW64 | gmake (64-bit Windows)
# Supported Version(s):    6.x
# ToolchainInfo Version:   2020a
# Specification Revision:  1.0
# 
#-------------------------------------------
# Macros assumed to be defined elsewhere
#-------------------------------------------

# C_STANDARD_OPTS
# CPP_STANDARD_OPTS
# MINGW_ROOT
# MINGW_C_STANDARD_OPTS

#-----------
# MACROS
#-----------

WARN_FLAGS            = -Wall -W -Wwrite-strings -Winline -Wstrict-prototypes -Wnested-externs -Wpointer-arith -Wcast-align
WARN_FLAGS_MAX        = $(WARN_FLAGS) -Wcast-qual -Wshadow
CPP_WARN_FLAGS        = -Wall -W -Wwrite-strings -Winline -Wpointer-arith -Wcast-align
CPP_WARN_FLAGS_MAX    = $(CPP_WARN_FLAGS) -Wcast-qual -Wshadow
MW_EXTERNLIB_DIR      = $(MATLAB_ROOT)/extern/lib/win64/mingw64
SHELL                 = %SystemRoot%/system32/cmd.exe

TOOLCHAIN_SRCS = 
TOOLCHAIN_INCS = 
TOOLCHAIN_LIBS = -lws2_32

#------------------------
# BUILD TOOL COMMANDS
#------------------------

# C Compiler: GNU C Compiler
CC_PATH = $(MINGW_ROOT)
CC = "$(CC_PATH)/gcc"

# Linker: GNU Linker
LD_PATH = $(MINGW_ROOT)
LD = "$(LD_PATH)/g++"

# C++ Compiler: GNU C++ Compiler
CPP_PATH = $(MINGW_ROOT)
CPP = "$(CPP_PATH)/g++"

# C++ Linker: GNU C++ Linker
CPP_LD_PATH = $(MINGW_ROOT)
CPP_LD = "$(CPP_LD_PATH)/g++"

# Archiver: GNU Archiver
AR_PATH = $(MINGW_ROOT)
AR = "$(AR_PATH)/ar"

# MEX Tool: MEX Tool
MEX_PATH = $(MATLAB_ARCH_BIN)
MEX = "$(MEX_PATH)/mex"

# Download: Download
DOWNLOAD =

# Execute: Execute
EXECUTE = $(PRODUCT)

# Builder: GMAKE Utility
MAKE_PATH = %MATLAB%/bin/win64
MAKE = "$(MAKE_PATH)/gmake"


#-------------------------
# Directives/Utilities
#-------------------------

CDEBUG              = -g
C_OUTPUT_FLAG       = -o
LDDEBUG             = -g
OUTPUT_FLAG         = -o
CPPDEBUG            = -g
CPP_OUTPUT_FLAG     = -o
CPPLDDEBUG          = -g
OUTPUT_FLAG         = -o
ARDEBUG             =
STATICLIB_OUTPUT_FLAG =
MEX_DEBUG           = -g
RM                  = @del
ECHO                = @echo
MV                  = @move
RUN                 =

#--------------------------------------
# "Faster Runs" Build Configuration
#--------------------------------------

ARFLAGS              = ruvs
CFLAGS               = -c $(MINGW_C_STANDARD_OPTS) -m64 \
                       -O3 -fno-loop-optimize -fno-aggressive-loop-optimizations
CPPFLAGS             = -c $(CPP_STANDARD_OPTS) -m64 -std=c++11 \
                       -O3 -fno-loop-optimize -fno-aggressive-loop-optimizations
CPP_LDFLAGS          = -Wl,-rpath,"$(MATLAB_ARCH_BIN)",-L"$(MATLAB_ARCH_BIN)" -static -m64
CPP_SHAREDLIB_LDFLAGS  = -shared -Wl,-rpath,"$(MATLAB_ARCH_BIN)",-L"$(MATLAB_ARCH_BIN)" -Wl,--no-undefined \
                         -Wl,--out-implib,$(basename $(PRODUCT)).lib
DOWNLOAD_FLAGS       =
EXECUTE_FLAGS        =
LDFLAGS              = -Wl,-rpath,"$(MATLAB_ARCH_BIN)",-L"$(MATLAB_ARCH_BIN)" -static -m64
MEX_CPPFLAGS         =
MEX_CPPLDFLAGS       =
MEX_CFLAGS           =
MEX_LDFLAGS          =
MAKE_FLAGS           = -f $(MAKEFILE)
SHAREDLIB_LDFLAGS    = -shared -Wl,-rpath,"$(MATLAB_ARCH_BIN)",-L"$(MATLAB_ARCH_BIN)" -Wl,--no-undefined \
                       -Wl,--out-implib,$(basename $(PRODUCT)).lib



###########################################################################
## OUTPUT INFO
###########################################################################

PRODUCT = ./ac_feat.lib
PRODUCT_TYPE = "static-library"
BUILD_TYPE = "Static Library"

###########################################################################
## INCLUDE PATHS
###########################################################################

INCLUDES_BUILDINFO = 

INCLUDES = $(INCLUDES_BUILDINFO)

###########################################################################
## DEFINES
###########################################################################

DEFINES_ = -D__USE_MINGW_ANSI_STDIO=1 -DMODEL=ac_feat
DEFINES_CUSTOM = 
DEFINES_STANDARD = -DMODEL=ac_feat

DEFINES = $(DEFINES_) $(DEFINES_CUSTOM) $(DEFINES_STANDARD)

###########################################################################
## SOURCE FILES
###########################################################################

SRCS = $(START_DIR)/rt_nonfinite.c $(START_DIR)/rtGetNaN.c $(START_DIR)/rtGetInf.c $(START_DIR)/ac_feat_rtwutil.c $(START_DIR)/ac_feat_data.c $(START_DIR)/ac_feat_initialize.c $(START_DIR)/ac_feat_terminate.c $(START_DIR)/ac_feat.c $(START_DIR)/isequal.c $(START_DIR)/xcorr.c $(START_DIR)/minOrMax.c $(START_DIR)/power.c $(START_DIR)/fft.c $(START_DIR)/any.c $(START_DIR)/FFTImplementationCallback.c $(START_DIR)/exp.c $(START_DIR)/ifft.c $(START_DIR)/findpeaks.c $(START_DIR)/eml_setop.c $(START_DIR)/sort.c $(START_DIR)/sortIdx.c $(START_DIR)/features.c $(START_DIR)/skewness.c $(START_DIR)/sum.c $(START_DIR)/nan_sum_or_mean.c $(START_DIR)/bsxfun.c $(START_DIR)/nanmean.c $(START_DIR)/kurtosis.c $(START_DIR)/vvarstd.c $(START_DIR)/mvksdensity.c $(START_DIR)/all.c $(START_DIR)/ecdf.c $(START_DIR)/diff.c $(START_DIR)/nullAssignment.c $(START_DIR)/linspace.c $(START_DIR)/log.c $(START_DIR)/mtimes.c $(START_DIR)/ac_feat_emxutil.c $(START_DIR)/ac_feat_emxAPI.c

ALL_SRCS = $(SRCS)

###########################################################################
## OBJECTS
###########################################################################

OBJS = rt_nonfinite.obj rtGetNaN.obj rtGetInf.obj ac_feat_rtwutil.obj ac_feat_data.obj ac_feat_initialize.obj ac_feat_terminate.obj ac_feat.obj isequal.obj xcorr.obj minOrMax.obj power.obj fft.obj any.obj FFTImplementationCallback.obj exp.obj ifft.obj findpeaks.obj eml_setop.obj sort.obj sortIdx.obj features.obj skewness.obj sum.obj nan_sum_or_mean.obj bsxfun.obj nanmean.obj kurtosis.obj vvarstd.obj mvksdensity.obj all.obj ecdf.obj diff.obj nullAssignment.obj linspace.obj log.obj mtimes.obj ac_feat_emxutil.obj ac_feat_emxAPI.obj

ALL_OBJS = $(OBJS)

###########################################################################
## PREBUILT OBJECT FILES
###########################################################################

PREBUILT_OBJS = 

###########################################################################
## LIBRARIES
###########################################################################

LIBS = 

###########################################################################
## SYSTEM LIBRARIES
###########################################################################

SYSTEM_LIBS = 

###########################################################################
## ADDITIONAL TOOLCHAIN FLAGS
###########################################################################

#---------------
# C Compiler
#---------------

CFLAGS_BASIC = $(DEFINES) $(INCLUDES) @$(COMPILER_COMMAND_FILE)

CFLAGS += $(CFLAGS_BASIC)

#-----------------
# C++ Compiler
#-----------------

CPPFLAGS_BASIC = $(DEFINES) $(INCLUDES) @$(COMPILER_COMMAND_FILE)

CPPFLAGS += $(CPPFLAGS_BASIC)

#---------------------
# MEX C++ Compiler
#---------------------

MEX_CPP_Compiler_BASIC =  @$(COMPILER_COMMAND_FILE)

MEX_CPPFLAGS += $(MEX_CPP_Compiler_BASIC)

#-----------------
# MEX Compiler
#-----------------

MEX_Compiler_BASIC =  @$(COMPILER_COMMAND_FILE)

MEX_CFLAGS += $(MEX_Compiler_BASIC)

###########################################################################
## INLINED COMMANDS
###########################################################################


MINGW_C_STANDARD_OPTS = $(C_STANDARD_OPTS)


###########################################################################
## PHONY TARGETS
###########################################################################

.PHONY : all build clean info prebuild download execute


all : build
	@echo "### Successfully generated all binary outputs."


build : prebuild $(PRODUCT)


prebuild : 


download : $(PRODUCT)


execute : download


###########################################################################
## FINAL TARGET
###########################################################################

#---------------------------------
# Create a static library         
#---------------------------------

$(PRODUCT) : $(OBJS) $(PREBUILT_OBJS)
	@echo "### Creating static library "$(PRODUCT)" ..."
	$(AR) $(ARFLAGS)  $(PRODUCT) @$(CMD_FILE)
	@echo "### Created: $(PRODUCT)"


###########################################################################
## INTERMEDIATE TARGETS
###########################################################################

#---------------------
# SOURCE-TO-OBJECT
#---------------------

%.obj : %.c
	$(CC) $(CFLAGS) -o "$@" "$<"


%.obj : %.cpp
	$(CPP) $(CPPFLAGS) -o "$@" "$<"


%.obj : $(RELATIVE_PATH_TO_ANCHOR)/%.c
	$(CC) $(CFLAGS) -o "$@" "$<"


%.obj : $(RELATIVE_PATH_TO_ANCHOR)/%.cpp
	$(CPP) $(CPPFLAGS) -o "$@" "$<"


%.obj : $(START_DIR)/%.c
	$(CC) $(CFLAGS) -o "$@" "$<"


%.obj : $(START_DIR)/%.cpp
	$(CPP) $(CPPFLAGS) -o "$@" "$<"


%.obj : C:/Users/ieedara/Desktop/Harman_files/09.29.2020/%.c
	$(CC) $(CFLAGS) -o "$@" "$<"


%.obj : C:/Users/ieedara/Desktop/Harman_files/09.29.2020/%.cpp
	$(CPP) $(CPPFLAGS) -o "$@" "$<"


rt_nonfinite.obj : $(START_DIR)/rt_nonfinite.c
	$(CC) $(CFLAGS) -o "$@" "$<"


rtGetNaN.obj : $(START_DIR)/rtGetNaN.c
	$(CC) $(CFLAGS) -o "$@" "$<"


rtGetInf.obj : $(START_DIR)/rtGetInf.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat_rtwutil.obj : $(START_DIR)/ac_feat_rtwutil.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat_data.obj : $(START_DIR)/ac_feat_data.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat_initialize.obj : $(START_DIR)/ac_feat_initialize.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat_terminate.obj : $(START_DIR)/ac_feat_terminate.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat.obj : $(START_DIR)/ac_feat.c
	$(CC) $(CFLAGS) -o "$@" "$<"


isequal.obj : $(START_DIR)/isequal.c
	$(CC) $(CFLAGS) -o "$@" "$<"


xcorr.obj : $(START_DIR)/xcorr.c
	$(CC) $(CFLAGS) -o "$@" "$<"


minOrMax.obj : $(START_DIR)/minOrMax.c
	$(CC) $(CFLAGS) -o "$@" "$<"


power.obj : $(START_DIR)/power.c
	$(CC) $(CFLAGS) -o "$@" "$<"


fft.obj : $(START_DIR)/fft.c
	$(CC) $(CFLAGS) -o "$@" "$<"


any.obj : $(START_DIR)/any.c
	$(CC) $(CFLAGS) -o "$@" "$<"


FFTImplementationCallback.obj : $(START_DIR)/FFTImplementationCallback.c
	$(CC) $(CFLAGS) -o "$@" "$<"


exp.obj : $(START_DIR)/exp.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ifft.obj : $(START_DIR)/ifft.c
	$(CC) $(CFLAGS) -o "$@" "$<"


findpeaks.obj : $(START_DIR)/findpeaks.c
	$(CC) $(CFLAGS) -o "$@" "$<"


eml_setop.obj : $(START_DIR)/eml_setop.c
	$(CC) $(CFLAGS) -o "$@" "$<"


sort.obj : $(START_DIR)/sort.c
	$(CC) $(CFLAGS) -o "$@" "$<"


sortIdx.obj : $(START_DIR)/sortIdx.c
	$(CC) $(CFLAGS) -o "$@" "$<"


features.obj : $(START_DIR)/features.c
	$(CC) $(CFLAGS) -o "$@" "$<"


skewness.obj : $(START_DIR)/skewness.c
	$(CC) $(CFLAGS) -o "$@" "$<"


sum.obj : $(START_DIR)/sum.c
	$(CC) $(CFLAGS) -o "$@" "$<"


nan_sum_or_mean.obj : $(START_DIR)/nan_sum_or_mean.c
	$(CC) $(CFLAGS) -o "$@" "$<"


bsxfun.obj : $(START_DIR)/bsxfun.c
	$(CC) $(CFLAGS) -o "$@" "$<"


nanmean.obj : $(START_DIR)/nanmean.c
	$(CC) $(CFLAGS) -o "$@" "$<"


kurtosis.obj : $(START_DIR)/kurtosis.c
	$(CC) $(CFLAGS) -o "$@" "$<"


vvarstd.obj : $(START_DIR)/vvarstd.c
	$(CC) $(CFLAGS) -o "$@" "$<"


mvksdensity.obj : $(START_DIR)/mvksdensity.c
	$(CC) $(CFLAGS) -o "$@" "$<"


all.obj : $(START_DIR)/all.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ecdf.obj : $(START_DIR)/ecdf.c
	$(CC) $(CFLAGS) -o "$@" "$<"


diff.obj : $(START_DIR)/diff.c
	$(CC) $(CFLAGS) -o "$@" "$<"


nullAssignment.obj : $(START_DIR)/nullAssignment.c
	$(CC) $(CFLAGS) -o "$@" "$<"


linspace.obj : $(START_DIR)/linspace.c
	$(CC) $(CFLAGS) -o "$@" "$<"


log.obj : $(START_DIR)/log.c
	$(CC) $(CFLAGS) -o "$@" "$<"


mtimes.obj : $(START_DIR)/mtimes.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat_emxutil.obj : $(START_DIR)/ac_feat_emxutil.c
	$(CC) $(CFLAGS) -o "$@" "$<"


ac_feat_emxAPI.obj : $(START_DIR)/ac_feat_emxAPI.c
	$(CC) $(CFLAGS) -o "$@" "$<"


###########################################################################
## DEPENDENCIES
###########################################################################

$(ALL_OBJS) : rtw_proj.tmw $(COMPILER_COMMAND_FILE) $(MAKEFILE)


###########################################################################
## MISCELLANEOUS TARGETS
###########################################################################

info : 
	@echo "### PRODUCT = $(PRODUCT)"
	@echo "### PRODUCT_TYPE = $(PRODUCT_TYPE)"
	@echo "### BUILD_TYPE = $(BUILD_TYPE)"
	@echo "### INCLUDES = $(INCLUDES)"
	@echo "### DEFINES = $(DEFINES)"
	@echo "### ALL_SRCS = $(ALL_SRCS)"
	@echo "### ALL_OBJS = $(ALL_OBJS)"
	@echo "### LIBS = $(LIBS)"
	@echo "### MODELREF_LIBS = $(MODELREF_LIBS)"
	@echo "### SYSTEM_LIBS = $(SYSTEM_LIBS)"
	@echo "### TOOLCHAIN_LIBS = $(TOOLCHAIN_LIBS)"
	@echo "### CFLAGS = $(CFLAGS)"
	@echo "### LDFLAGS = $(LDFLAGS)"
	@echo "### SHAREDLIB_LDFLAGS = $(SHAREDLIB_LDFLAGS)"
	@echo "### CPPFLAGS = $(CPPFLAGS)"
	@echo "### CPP_LDFLAGS = $(CPP_LDFLAGS)"
	@echo "### CPP_SHAREDLIB_LDFLAGS = $(CPP_SHAREDLIB_LDFLAGS)"
	@echo "### ARFLAGS = $(ARFLAGS)"
	@echo "### MEX_CFLAGS = $(MEX_CFLAGS)"
	@echo "### MEX_CPPFLAGS = $(MEX_CPPFLAGS)"
	@echo "### MEX_LDFLAGS = $(MEX_LDFLAGS)"
	@echo "### MEX_CPPLDFLAGS = $(MEX_CPPLDFLAGS)"
	@echo "### DOWNLOAD_FLAGS = $(DOWNLOAD_FLAGS)"
	@echo "### EXECUTE_FLAGS = $(EXECUTE_FLAGS)"
	@echo "### MAKE_FLAGS = $(MAKE_FLAGS)"


clean : 
	$(ECHO) "### Deleting all derived files..."
	$(RM) $(subst /,\,$(PRODUCT))
	$(RM) $(subst /,\,$(ALL_OBJS))
	$(ECHO) "### Deleted all derived files."


