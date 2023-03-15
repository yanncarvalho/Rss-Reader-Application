package br.dev.yann.rssreader.auth.user;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.yanncarvalho.rssreader.auth.user.User;
import io.github.yanncarvalho.rssreader.auth.user.UserRepository;
import io.github.yanncarvalho.rssreader.auth.user.UserRole;
import io.github.yanncarvalho.rssreader.auth.user.UserService;
import io.github.yanncarvalho.rssreader.auth.user.exception.UserSingleAdminException;
import io.github.yanncarvalho.rssreader.auth.user.exception.UsernameNotUniqueException;
import io.github.yanncarvalho.rssreader.auth.user.record.SaveReq;
import io.github.yanncarvalho.rssreader.auth.user.record.UpdateAsAdminReq;
import io.github.yanncarvalho.rssreader.auth.user.record.UpdateReq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {
	
	@Mock
	private UserRepository repositoryMock;
	
    @InjectMocks
	private UserService serviceMock;

	@Test
	@DisplayName("if call findAllUsers then call Repository.findAll")
	void testIfCallFindAllUsersThenCallRepository_findAll() {
		
        //Arrange
		var pageFake = Pageable.unpaged();
		serviceMock.findAllUsers(pageFake);
		
		//Assert
		verify(repositoryMock).findAll(pageFake);
	}
	
	@Test
	@DisplayName("if call deleteUser then call Repository.existsSoleUserAdminById")
	void testIfCallDeleteUserThenCallRepository_existsSoleUserAdminById() {
		
        //Arrange
		var anyUUID = UUID.randomUUID();
		
		//Act
		serviceMock.deleteUser(anyUUID);
		
		//Assert
		verify(repositoryMock).existsSoleUserAdminById(anyUUID);
	}
	
	@Test
	@DisplayName("if try to delete a user that is the singleAdmin then throw UserSingleAdminException")
	void testIfTryDeleteUserSingleAdminThenThrowUserSingleAdminException() {
		
        //Arrange
		var anyUUID = UUID.randomUUID();
		
		when(repositoryMock.existsSoleUserAdminById(anyUUID)).thenReturn(Boolean.TRUE);

		//Assert
		assertThrows(UserSingleAdminException.class, () -> 	serviceMock.deleteUser(anyUUID));
	}

	@Test
	@DisplayName("if call deleteUser then call Repository.deleteById")
	void testIfCallDeleteUserThenCallRepository_deleteById() {
		
        //Arrange
		UUID anyUUID = UUID.randomUUID();
		
		//Act
		serviceMock.deleteUser(anyUUID);
		
		//Assert
		verify(repositoryMock).deleteById(anyUUID);
	}
	
	@Test
	@DisplayName("if call save then call Repository.save")
	void testIfCallSaveThenCallRepository_saved() {
		
        //Arrange
		var saveUserMock = new SaveReq(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
		
		when(repositoryMock.existsByUsername(saveUserMock.username())).thenReturn(Boolean.FALSE);
		
		//Act
		serviceMock.save(saveUserMock);
		
		//Assert
		verify(repositoryMock).save(new User(saveUserMock.name(), saveUserMock.password(), saveUserMock.username()));
	}
	
	@Test
	@DisplayName("if try to save a new User with username already used then throw UsernameNotUniqueException")
	void testIfTrySaveAUserWithUsernameUsedThenThrowUsernameNotUniqueException() {
		
        //Arrange
		var saveUserMock = new SaveReq(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
		
		when(repositoryMock.existsByUsername(saveUserMock.username())).thenReturn(Boolean.TRUE);

		//Assert
		assertThrows(UsernameNotUniqueException.class, () -> serviceMock.save(saveUserMock));
	}
	
	@Test
	@DisplayName("if call findById then call Repository.findById")
	void testIfCallfindByIdThenCallRepository_findById() {
		
        //Arrange
		var anyUUID = UUID.randomUUID();
		
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.of(new User()));
				
		//Act
		serviceMock.findById(anyUUID);
		
		//Assert
		verify(repositoryMock).findById(anyUUID);
	}
	
	@Test
	@DisplayName("if try to findById and Id not exists then throw UsernameNotFoundException")
	void testIfTryFindByIdAndIdNotExistsThenThrowUsernameNotFoundException() {
		
        //Arrange
		var anyUUID = UUID.randomUUID();
		
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.empty());
		
		//Assert
		assertThrows(UsernameNotFoundException.class, () -> serviceMock.findById(anyUUID), USER_NOT_FOUND);
	}
	
	@Test
	@DisplayName("if call loadUserByUsername then call Repository.findByUsername")
	void testIfCallLoadUserByUsernameThenCallRepository_findByUsername() {
		
        //Arrange
		when(repositoryMock.findByUsername(StringUtils.EMPTY)).thenReturn(Optional.of(new User()));
				
		//Act
		serviceMock.loadUserByUsername(StringUtils.EMPTY);
		
		//Assert
		verify(repositoryMock).findByUsername(StringUtils.EMPTY);
	}
	
	@Test
	@DisplayName("if try to loadUserByUsername and Id not exists then throw UsernameNotFoundException")
	void testIfTryLoadUserByUsernameAndIdNotExistsThenThrowUsernameNotFoundException() {
		
        //Arrange
		when(repositoryMock.findByUsername(StringUtils.EMPTY)).thenReturn(Optional.empty());
		
		//Assert
		assertThrows(UsernameNotFoundException.class, () -> serviceMock.loadUserByUsername(StringUtils.EMPTY), USER_NOT_FOUND);
	}
	
	@Test
	@DisplayName("if try to update a user that the username already used then throw UsernameNotUniqueException")
	void testIfTryUpdateUserWithUsernameUsedThenThrowUserSingleAdminException() {
		
        //Arrange
		var anyUUID = UUID.randomUUID();
		var userMock = mock(User.class);
		var updateUser = new UpdateReq(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
		
		when(repositoryMock.findByUsername(StringUtils.EMPTY)).thenReturn(Optional.of(userMock));
		when (userMock.getId()).thenReturn(UUID.randomUUID());

		//Assert
		assertThrows(UsernameNotUniqueException.class, () -> serviceMock.update(updateUser, anyUUID));
	}
	
	@Test
	@DisplayName("if try to update a user that then set user new values")
	void testIfTryUpdateUserWithUsernameUsedThenSetUserValues() {
		
		//Arrange
		var anyName = "ANY_NAME";
		var anyUsername = "ANY_USERNAME";
		var anyPassword = "ANY_PASSWORD";
		var anyUUID = UUID.randomUUID();
		var userMock = mock(User.class);
		var updateUser = new UpdateReq(anyName, anyUsername, anyPassword);
		
		when(repositoryMock.findByUsername(anyUsername)).thenReturn(Optional.empty());
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.of(userMock));
		when(userMock.getId()).thenReturn(anyUUID);
		
		//Act 
	    serviceMock.update(updateUser, anyUUID);
		
	    //Assert
		verify(userMock, times(1)).setName(anyName);
		verify(userMock, times(1)).setUsername(anyUsername);
		verify(userMock, times(1)).setPassword(anyPassword);
	}
	
	@Test
	@DisplayName("if call update then call repository.save")
	void testIfCallUpdateThenCallRepository_save() {
		
		//Arrange
		var anyUUID = UUID.randomUUID();
		var userMock = mock(User.class);
		var updateUser = new UpdateReq(null, null, null);
		
		when(repositoryMock.findByUsername(null)).thenReturn(Optional.empty());
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.of(userMock));
		when(userMock.getId()).thenReturn(anyUUID);

		//Act 
	    serviceMock.update(updateUser, anyUUID);
		
		//Assert
		verify(repositoryMock).save(userMock);
	}
	
	@Test
	@DisplayName("if try to update as admin a user that the username already used then throw UsernameNotUniqueException")
	void testIfTryupdateAsAdminUserWithUsernameUsedThenThrowUserSingleAdminException() {

        //Arrange
		var anyUUID = UUID.randomUUID();
		var anyRole = UserRole.USER;
		var userMock = mock(User.class);
		var updateUser = new UpdateAsAdminReq(anyUUID, StringUtils.EMPTY, StringUtils.EMPTY, anyRole);
		
		when(repositoryMock.findByUsername(StringUtils.EMPTY)).thenReturn(Optional.of(userMock));
		when (userMock.getId()).thenReturn(UUID.randomUUID());

		//Assert
		assertThrows(UsernameNotUniqueException.class, () -> serviceMock.updateAsAdmin(updateUser));
	}
	
	@Test
	@DisplayName("if try to update as admin a user that then set user new values")
	void testIfTryUpdateAsAdminUserWithUsernameUsedThenSetUserValues() {
		
		//Arrange
		var anyName = "ANY_NAME";
		var anyUsername = "ANY_USERNAME";;
		var anyUUID = UUID.randomUUID();
		var userMock = mock(User.class);
		var anyRole = UserRole.USER;
		var updateUser = new UpdateAsAdminReq(anyUUID, anyName, anyUsername, anyRole);
		
		when(userMock.getId()).thenReturn(anyUUID);
		
		when(repositoryMock.findByUsername(anyUsername)).thenReturn(Optional.empty());
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.of(userMock));
		when(repositoryMock.existsSoleUserAdminById(anyUUID)).thenReturn(Boolean.FALSE);
	
		//Act 
	    serviceMock.updateAsAdmin(updateUser);
		
	    //Assert
		verify(userMock, times(1)).setName(anyName);
		verify(userMock, times(1)).setUsername(anyUsername);
		verify(userMock, times(1)).setRole(anyRole);
	}
	
	@Test
	@DisplayName("if call updateAsAdmin then call repository.save")
	void testIfCallUpdateAsAdminThenCallRepository_save() {
		
		//Arrange
		var anyUUID = UUID.randomUUID();
		var userMock = mock(User.class);
		var updateUser = new UpdateAsAdminReq(anyUUID, null, null, null);
		
		when(repositoryMock.findByUsername(null)).thenReturn(Optional.empty());
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.of(userMock));
		when(userMock.getId()).thenReturn(anyUUID);

		//Act 
	    serviceMock.updateAsAdmin(updateUser);
		
		//Assert
		verify(repositoryMock).save(userMock);
	}
	
	@Test
	@DisplayName("if try to update as admin the only user with admin role to USER role then throw UserSingleAdminException")
	void testIfTryUpdateOnlyUserAdminRoleToUserThenthrowUserSingleAdminException() {
		
		//Arrange
		var anyUUID = UUID.randomUUID();
		var userMock = mock(User.class);
		var anyRole = UserRole.USER;
		var updateUser = new UpdateAsAdminReq(anyUUID, null, null, anyRole);
		
		when(repositoryMock.findByUsername(null)).thenReturn(Optional.empty());
		when(repositoryMock.findById(anyUUID)).thenReturn(Optional.of(userMock));
		when(userMock.getId()).thenReturn(anyUUID);
		
		when(repositoryMock.existsSoleUserAdminById(anyUUID)).thenReturn(Boolean.TRUE);
		
		//Assert
		assertThrows(UserSingleAdminException.class, () -> serviceMock.updateAsAdmin(updateUser));
	}


}
