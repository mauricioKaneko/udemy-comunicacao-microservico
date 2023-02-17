import { Router } from 'express';

import UserController from '../controller/UserController.js';
import CheckToken from '../../../auth/CheckToken.js';

const router = new Router();
router.post('/api/user/auth', UserController.getAcessToken);

router.use(CheckToken);

router.get('/api/user/email/:email', UserController.findByEmail);

export default router;